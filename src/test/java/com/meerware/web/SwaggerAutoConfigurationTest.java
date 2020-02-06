package com.meerware.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.env.Environment;

import springfox.documentation.PathProvider;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.service.contexts.Defaults;
import springfox.documentation.spi.service.contexts.DocumentationContextBuilder;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Tests for the {@link SwaggerAutoConfiguration}.
 */
@RunWith(MockitoJUnitRunner.class)
public class SwaggerAutoConfigurationTest {

    /**
     * Main test object.
     */
    @InjectMocks
    private SwaggerAutoConfiguration configuraiton;

    /**
     * {@link Mock} {@link Environment}.
     */
    @Mock
    private Environment environment;

    /**
     * Sets up the mocks.
     */
    @Before
    public void setUp() {
        when(environment.getProperty("application.title"))
            .thenReturn("Application Title");
        when(environment.getProperty("application.version"))
        .thenReturn("1.0.0");
    }

    /**
     * Ensures provision of the {@code Swagger} {@link Docket}.
     */
    @Test
    public void shouldProvideSwaggerDocket() {
        ServerProperties properties = new ServerProperties();
        properties.getServlet().setContextPath("/context");
        ApiInfo info = mock(ApiInfo.class);
        Docket docket = configuraiton.swaggerDocklet(properties, info);

        DocumentationContextBuilder builder = spy(new DocumentationContextBuilder(SWAGGER_2));
        docket.configure(builder);
        verify(builder).apiInfo(info);

        ArgumentCaptor<PathProvider> captor = ArgumentCaptor.forClass(PathProvider.class);
        verify(builder).pathProvider(captor.capture());

        PathProvider pathProvider = captor.getValue();
        assertEquals("/context", pathProvider.getApplicationBasePath());
    }

    /**
     * Ensures provision of the {@code Swagger} {@link ApiInfo}.
     */
    @Test
    public void shouldProvideSwaggerApiInfo() {
        ApiInfo info = configuraiton.swaggerApiInfo(environment);
        assertEquals("Application Title", info.getTitle());
        assertEquals("1.0.0", info.getVersion());
    }

    /**
     * Ensures provision of the {@code Swagger} {@link Defaults}.
     */
    @Test
    public void shouldProvideSwaggerDefaults() {
        Defaults defaults = configuraiton.swaggerDefaults();
        assertTrue(defaults.defaultResponseMessages().isEmpty());
    }
}
