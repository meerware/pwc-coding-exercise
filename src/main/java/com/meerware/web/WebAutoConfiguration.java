package com.meerware.web;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CACHE_CONTROL;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.common.base.Joiner;

/**
 * Auto {@link Configuration} for web components, mostly around handling errors and {@code CORS}.
 */
@Configuration
@AutoConfigureBefore({ ErrorMvcAutoConfiguration.class })
class WebAutoConfiguration implements WebMvcConfigurer {

    /**
     * Log instance.
     */
    private static final Logger LOG = getLogger(WebAutoConfiguration.class);

    /**
     * Default CORS allowed methods.
     */
    private static final String[] DEFAULT_ALLOWED_METHODS
            = {"GET", "POST", "PUT", "DELETE"};

    private static final String[] DEFAULT_ALLOWED_HEADERS
            = {CONTENT_TYPE, ACCEPT};

    private static final String[] DEFAULT_EXPOSED_HEADERS
            = {CONTENT_TYPE, CACHE_CONTROL};

    private final Environment environment;

    @Autowired
    WebAutoConfiguration(Environment environment) {
        this.environment = environment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        final String[] origins = environment.getProperty(
                "spring.web.cors.allowed-origins",
                String[].class,
                new String[]{"*"});


        final String[] methods = environment.getProperty(
                "spring.web.cors.allowed-methods",
                String[].class,
                DEFAULT_ALLOWED_METHODS);

        final String[] allowedHeaders = environment.getProperty(
                "spring.web.cors.allowed-headers",
                String[].class,
                DEFAULT_ALLOWED_HEADERS);

        final String[] exposedHeaders = environment.getProperty(
                "spring.web.cors.exposed-headers",
                String[].class,
                DEFAULT_EXPOSED_HEADERS);

        registry.addMapping("/**")
                .allowedOrigins(origins)
                .allowedMethods(methods)
                .allowedHeaders(allowedHeaders)
                .exposedHeaders(exposedHeaders);

        LOG.info("Cross origin resource sharing restrictions registered:"
                + "allowed origins=\"{}\", allowed methods=\"{}\", allowed headers=\"{}\", exposed headers=\"{}\"",
                Joiner.on("\", \"").join(origins),
                Joiner.on("\", \"").join(methods),
                Joiner.on("\", \"").join(allowedHeaders),
                Joiner.on("\", \"").join(exposedHeaders));
    }


    /**
     * @param source is the {@link MessageSourceAccessor}.
     * @return the {@link ErrorAttributes}.
     */
    @Bean
    ErrorAttributes errorAttributes(MessageSourceAccessor source) {
        return new ErrorsAttributes(source);
    }


    /**
     * @param properties is the {@link ServerProperties}.
     * @return the {@link ErrorProperties}.
     */
    @Bean
    ErrorProperties errorProperties(ServerProperties properties) {
        ErrorProperties error = properties.getError();

        error.getWhitelabel().setEnabled(false);

        return error;
    }
}
