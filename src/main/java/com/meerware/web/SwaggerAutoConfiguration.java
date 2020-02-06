package com.meerware.web;

import java.util.List;
import java.util.Map;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.ImmutableMap;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.service.contexts.Defaults;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

/**
 * Auto {@link Configuration} for {@code Swagger}.
 */
@Configuration
@EnableSwagger2
class SwaggerAutoConfiguration {

    /**
     * @param serverProperties is the  {@link ServerProperties} used to configure the path provider.
     * @param apiInfo is the {@link ApiInfo}.
     *
     * @return the {@code Swagger} {@link Docklet} bean.
     */
    @Bean
    Docket swaggerDocklet(
            ServerProperties serverProperties,
            ApiInfo apiInfo) {
        // Paths are made up of any paths but we ignore any of the .json
        // paths as they just duplicate things and dirty up the UI
        return new Docket(SWAGGER_2)
                .pathProvider(new AbstractPathProvider() {
                    @Override
                    protected String getDocumentationPath() {
                        return "/admin";
                    }
                    @Override
                    protected String applicationPath() {
                        return serverProperties.getServlet().getContextPath();
                    }
                })
                .apiInfo(apiInfo)
                .select()
                .paths(input -> !input.endsWith(".json"))
                .build();
    }

    /**
     * @param environment is the {@code Spring} {@link Environment}.
     *
     * @return the {@code Swagger} {@link ApiInfo}.
     */
    @Bean
    ApiInfo swaggerApiInfo(final Environment environment) {
        return new ApiInfoBuilder()
                .title(environment.getProperty("application.title"))
                .description(environment.getProperty("application.description"))
                .version(environment.getProperty("application.version"))
                .termsOfServiceUrl("")
                .license("")
                .licenseUrl("")
                .build();
    }


    /**
     * @return a {@link Defaults} which is used by {@code Swagger}. This is overridden as we don't want
     *         their defaults.
     */
    @Bean
    Defaults swaggerDefaults() {
        Defaults defaults = new Defaults() {
            @Override
            public Map<RequestMethod, List<ResponseMessage>> defaultResponseMessages() {
                return ImmutableMap.of();
            }
        };
        return defaults;
    }
}
