package com.meerware.web;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.DeserializationFeature.READ_ENUMS_USING_TO_STRING;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_ENUMS_USING_TO_STRING;
import java.util.TimeZone;

import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto {@link Configuration} used to configure the {@code Jackson} components.
 */
@Configuration
class JacksonAutoConfiguration {

    /**
     * ISO 8601 datetime format.
     */
    private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * @return the {@link JacksonProperties} with a few bonus features enabled to add a little flexibility
     *         to the JSON serialization and deserialization.
     */
    @Bean
    JacksonProperties jacksonProperties() {
        final JacksonProperties jacksonProperties = new JacksonProperties();
        jacksonProperties.setTimeZone(TimeZone.getDefault());
        jacksonProperties.setDateFormat(DATETIME_FORMAT);
        jacksonProperties.getDeserialization().put(FAIL_ON_UNKNOWN_PROPERTIES, false);
        jacksonProperties.getDeserialization().put(ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        jacksonProperties.getDeserialization().put(READ_ENUMS_USING_TO_STRING, true);
        jacksonProperties.getSerialization().put(WRITE_ENUMS_USING_TO_STRING, true);
        return jacksonProperties;
    }
}
