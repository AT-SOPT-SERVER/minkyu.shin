package org.sopt.global.config;

import org.sopt.global.annotation.V1;
import org.sopt.global.security.jwt.RefreshTokenResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RefreshTokenResolver refreshTokenResolver;

    public WebConfig(RefreshTokenResolver refreshTokenResolver) {
        this.refreshTokenResolver = refreshTokenResolver;
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer
                .addPathPrefix("/v1", HandlerTypePredicate.forAnnotation(V1.class))
                .setPathMatcher(new AntPathMatcher())
                .setUrlPathHelper(new UrlPathHelper())
        ;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(refreshTokenResolver);
    }
}