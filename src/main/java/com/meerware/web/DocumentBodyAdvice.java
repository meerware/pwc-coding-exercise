package com.meerware.web;

import static com.google.common.collect.ImmutableMap.of;
import static com.google.common.collect.Maps.newLinkedHashMap;
import static com.google.common.io.ByteStreams.toByteArray;
import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;
import static org.springframework.http.MediaType.parseMediaType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import com.meerware.Application;


/**
 * {@link ControllerAdvice} which unwraps request {@code JSON} and wraps
 * outgoing response {@code JSON} allowing the controllers to only need
 * to work with transfer objects directly.
 */
@ControllerAdvice(basePackageClasses = Application.class)
@Order(LOWEST_PRECEDENCE)
public class DocumentBodyAdvice extends RequestBodyAdviceAdapter
       implements RequestBodyAdvice, ResponseBodyAdvice<Object> {

    /**
     * {@link MediaType} which is handled specially. This wraps the output object
     * in a structure that matches the {@code JSON API} structure.
     */
    private static final MediaType JSON_API_MEDIA_TYPE = parseMediaType(
            "application/vnd.api+json");

    /**
     * Data component.
     */
    private static final String DATA = "data";

    /**
     * Attributes component, used for {@code JSON API}.
     */
    private static final String ATTRIBUTES = "attributes";

    /**
     * Identifier component, used for {@code JSON API}.
     */
    private static final String ID = "id";

    /**
     * Type component, used for {@code JSON API}.
     */
    private static final String TYPE = "type";

    /**
     * JSON {@link ObjectMapper}.
     */
    private final ObjectMapper mapper;

    /**
     * @param mapper is the {@link ObjectMapper}.
     */
    @Autowired
    DocumentBodyAdvice(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(
            MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converter) {
        // Only handle Jackson/JSON based requests
        if (!AbstractJackson2HttpMessageConverter.class.isAssignableFrom(converter)) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(
            MethodParameter methodParameter,
            Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return supports(methodParameter, converterType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> converter,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        final MappingJacksonValue container;
        if (body instanceof MappingJacksonValue) {
            container = (MappingJacksonValue) body;
        } else if (body != null) {
            container = new MappingJacksonValue(body);
        } else {
            return null;
        }

       final HttpHeaders headers = request.getHeaders();

        // If we are dealing with JSON API, we break the object into attributes
        // Otherwise, at the very least, we wrap in a data top level attribute
        if (JSON_API_MEDIA_TYPE.isCompatibleWith(headers.getContentType())) {
            // JSON API
            final Function<Object, Map<String, Object>> transform
                = input -> {
                Map<String, Object> transformed = mapper.convertValue(
                        input, new TypeReference<Map<String, Object>>() {
                });

                Map<String, Object> output = newLinkedHashMap();
                if (transformed.containsKey(ID)) {
                    output.put(ID, transformed.get(ID));
                }

                if (transformed.containsKey(TYPE)) {
                    output.put(TYPE, transformed.get(TYPE));
                } else {
                    output.put(TYPE, lowerCase(input.getClass().getSimpleName()));
                }

                // Everything else goes into attributes
                Map<String, Object> attributes = newLinkedHashMap(transformed);
                attributes.remove(ID);
                attributes.remove(TYPE);
                output.put(ATTRIBUTES, attributes);

                return output;
            };

            final Object value = container.getValue();
            if (value instanceof Iterable) {
                Iterable<?> iterable = (Iterable<?>) value;
                container.setValue(of(DATA, Iterables.transform(iterable, transform)));
            } else {
                container.setValue(of(DATA, transform.apply(value)));
            }
        } else {
            // Plain old JSON
            // Wrap in a map with a single data attribute
            container.setValue(of(DATA, container.getValue()));
        }

        return container;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public HttpInputMessage beforeBodyRead(
            HttpInputMessage inputMessage,
            MethodParameter parameter,
            Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        // Unwrap the data part of the input message
        final HttpHeaders headers = inputMessage.getHeaders();

        final byte[] bytes = toByteArray(inputMessage.getBody());

        final JsonNode root = mapper.readTree(bytes);

        // Whatever happens, we need to move to the data node if it exists
        final JsonNode data;
        if (root.isObject() && root.has(DATA)) {
            data = root.get(DATA);
        } else {
            data = root;
        }

        // Now it depends on what sort of media type (via content type)
        final InputStream body;
        if (JSON_API_MEDIA_TYPE.isCompatibleWith(headers.getContentType())) {
            // JSON API
            // We only handle "attributes", "id" and "type" part of the specification

            // Transform from an json node representing a JSON API document to a plain
            // map
            final Function<JsonNode, ObjectNode> transform
                = node -> {
                ObjectNode output = new ObjectNode(mapper.getNodeFactory());

                // Look for an object of attributes
                if (node.has(ATTRIBUTES) && node.get(ATTRIBUTES).isObject()) {
                    // Populate the map with the key values of the attribute
                    ObjectNode attributes = (ObjectNode) node.get(ATTRIBUTES);
                    output.setAll(attributes);
                }

                // Append (or replace) the ID with the one at the type of the document
                if (node.has(ID)) {
                    output.set(ID, node.get(ID));
                }

                return output;
            };


            final JsonNode output;
            if (data.isArray()) {
                // Transform each array item
                output = new ArrayNode(
                        mapper.getNodeFactory(),
                        Streams.stream((ArrayNode) data)
                                .map(transform)
                               .collect(Collectors.toList()));

            } else {
                output = transform.apply(data);
            }
            body = new ByteArrayInputStream(mapper.writeValueAsBytes(output));
        } else {
            // Plain old JSON
            body = new ByteArrayInputStream(mapper.writeValueAsBytes(data));
        }

        return new HttpInputMessage() {
            @Override
            public HttpHeaders getHeaders() {
                return headers;
            }
            @Override
            public InputStream getBody() throws IOException {
                return body;
            }
        };
    }

}
