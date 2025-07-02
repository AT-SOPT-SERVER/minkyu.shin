package org.sopt.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.customizers.OpenApiCustomizer;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    private static final String ACCESS_SCHEME = "AccessToken";
    private static final String REFRESH_SCHEME = "Refresh-Token";

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme accessTokenSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityScheme refreshTokenSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("Refresh-Token");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(ACCESS_SCHEME)
                .addList(REFRESH_SCHEME);

        Components components = new Components()
                .addSecuritySchemes(ACCESS_SCHEME, accessTokenSecurityScheme)
                .addSecuritySchemes(REFRESH_SCHEME, refreshTokenSecurityScheme);

        return new OpenAPI()
                .info(apiInfo())
                .security(Collections.singletonList(securityRequirement))
                .components(components);
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(openApi -> {
                    openApi.addSecurityItem(new SecurityRequirement().addList(ACCESS_SCHEME));
                })
                .build();
    }

    private Info apiInfo() {
        return new Info()
                .title("AT SOPT")
                .description("무서버 과제 API 문서")
                .version("1.0.0");
    }
}