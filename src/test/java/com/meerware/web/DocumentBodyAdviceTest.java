package com.meerware.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

/**
 * Tests for the {@link DocumentBodyAdvice}.
 */
@RunWith(MockitoJUnitRunner.class)
public class DocumentBodyAdviceTest {

    /**
     * Main test object.
     */
    private DocumentBodyAdvice advice;

    /**
     * {@link Mock} {@link ServerHttpRequest} used as test input.
     */
    @Mock
    private ServerHttpRequest request;

    /**
     * {@link Mock} {@link ServerHttpResponse} used as test input.
     */
    private ServerHttpResponse response;

    /**
     * Sets up the test object.
     */
    @Before
    public void setUp() {
        advice = new DocumentBodyAdvice(new ObjectMapper());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        when(request.getHeaders()).thenReturn(headers);
    }

    /**
     * Ensures supports with {@code Jackson} converter.
     */
    @Test
    public void shouldSupportWithJackson2Converter() {
        assertTrue(advice.supports(null, null, MappingJackson2HttpMessageConverter.class));
    }

    /**
     * Ensures does not support non {@code Jackson} converter.
     */
    @Test
    public void shouldDoesNotSupportWithNonJackson2Converter() {
        assertFalse(advice.supports(null, null, ByteArrayHttpMessageConverter.class));
    }

    /**
     * Ensures the before body write can handle a {@code null} body.
     */
    @Test
    public void shouldHandleNullBodyBeforeBodyWrite() {
        assertNull(advice.beforeBodyWrite(
                null,
                null,
                MediaType.APPLICATION_JSON,
                MappingJackson2HttpMessageConverter.class,
                request,
                response));
    }

    /**
     * Ensures wrapping the body in a {@code data} attribute when a plain {@code JSON} request is made.
     */
    @Test
    public void shouldWrapPlainJsonWithDataKeyBeforeBodyWrite() {
        Map<String, Object> body = ImmutableMap.of("value", 1);

        Object result = advice.beforeBodyWrite(
                body,
                null,
                MediaType.APPLICATION_JSON,
                MappingJackson2HttpMessageConverter.class,
                request,
                response);

        assertNotNull(result);
        assertTrue(result instanceof MappingJacksonValue);
        assertTrue(((MappingJacksonValue) result).getValue() instanceof Map);

        Map<?, ?> map = (Map<?, ?>) ((MappingJacksonValue) result).getValue();
        assertTrue(map.containsKey("data"));
        assertEquals(body, map.get("data"));
    }
}
