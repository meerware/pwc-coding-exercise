package com.meerware;


import static java.util.Optional.ofNullable;
import static org.springframework.boot.Banner.Mode.LOG;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;

import com.google.common.collect.ImmutableMap;

/**
 * Main entry point for the {@code Spring Boot} application.
 */
@SpringBootApplication
@EnableConfigurationProperties
public class Application extends SpringBootServletInitializer  {

    /**
     * Title environment variable name.
     */
    private static final String TITLE = "application.title";

    /**
     * Version environment variable name.
     */
    private static final String VERSION = "application.version";

    /**
     * Default name.
     */
    private static final String DEFAULT_TITLE = "Meerware Contacts";

    /**
     * Default version.
     */
    private static final String DEFAULT_VERSION = "1.0.0";

    /**
     * Default vendor.
     */
    private static final String DEFAULT_VENDOR = "Meerware";

    /**
     * Default description.
     */
    private static final String DEFAULT_DESCRIPTION = "Meerware Contacts API";

    /**
     * {@inheritDoc}
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.bannerMode(LOG);
        final String title = ofNullable(Application.class.getPackage().getImplementationTitle()).orElse(DEFAULT_TITLE);
        final String version = ofNullable(Application.class.getPackage().getImplementationVersion()).orElse(DEFAULT_VERSION);
        final String vendor = ofNullable(Application.class.getPackage().getImplementationVendor()).orElse(DEFAULT_VENDOR);

        final ImmutableMap.Builder<String, Object> properties = ImmutableMap.<String, Object>builder()
                .put("spring.application.name", title)
                .put(TITLE, title)
                .put(VERSION, version)
                .put("application.vendor", vendor)
                .put("application.description", DEFAULT_DESCRIPTION)
                .put("server.error.whitelabel.enabled", "false")
                .put("spring.profiles.active", "default")
                .put("spring.jpa.open-in-view", "true")
                .put("spring.jpa.show-sql", "true")
                .put("spring.jpa.generate-ddl", "false")
                .put("spring.jpa.hibernate.ddl-auto", "none")
                .put("spring.flyway.enabled", "true")
                .put("management.endpoints.web.base", "path=/admin")
                .put("management.endpoints.web.exposure.include", "*")
                .put("management.endpoint.health.show-details", "always")
                .put("management.endpoint.health.cache.time-to-live", "30000ms")

                .put("springfox.documentation.swagger.v2.path", "/admin/swagger/v2/api-docs");

        return builder.sources(Application.class)
                      .properties(properties.build());
    }

    /**
     * Provides a {@link MessageSourceAccessor} which is a handy wrapper around a {@link MessageSource} which
     * handles locale for you.
     *
     * @param messageSource is the {@link MessageSource} to wrap.
     * @return a {@link MessageSourceAccessor} which wraps the {@link MessageSource} bean.
     */
    @Bean
    MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource);
    }

    /**
     * @param arguments is the command line argument array.
     */
    public static void main(String[] arguments) {
        final Application main = new Application();
        main.run(main.configure(
                new SpringApplicationBuilder(Application.class)).build());
    }

}
