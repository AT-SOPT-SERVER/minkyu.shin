package org.sopt.global.security;

public class SecurityConstants {

    // 인증이 필요없는 경로들
    public static final String[] PUBLIC_URLS = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/actuator/**"
    };

    public static final String[] PUBLIC_API_URLS = {
            "/v1/api/auth/login",
            "/v1/api/auth/social/**",
    };


    // 관리자만 접근 가능한 경로들
    public static final String[] ADMIN_URLS = {
            "/admin/**",
            "/api/admin/**"
    };
}