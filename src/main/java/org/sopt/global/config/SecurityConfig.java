package org.sopt.global.config;


import lombok.RequiredArgsConstructor;
import org.sopt.global.security.SecurityConstants;
import org.sopt.global.security.filter.JwtAuthenticationFilter;
import org.sopt.global.security.filter.JwtExceptionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // CORS 설정
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // csrf 비활성화
                .csrf(csrf -> csrf.disable())

                // 폼 로그인 비활성화
                .formLogin(form -> form.disable())

                // HTTP Basic 인증 비활성화
                .httpBasic(basic -> basic.disable())

                // 권한 설정 - 상수 사용
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SecurityConstants.PUBLIC_URLS).permitAll()
                        .requestMatchers(SecurityConstants.PUBLIC_API_URLS).permitAll()
                        .requestMatchers(SecurityConstants.ADMIN_URLS).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                // 세션 관리 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // JWT 필터 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 허용할 origin
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:8080"
                // 실제 배포 도메인 추가
        ));

        // 허용할 HTTP 메서드
        configuration.setAllowedMethods(Arrays.asList("*"));

        // 허용할 헤더
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // 인증 정보 허용 (쿠키, Authorization 헤더 등)
        configuration.setAllowCredentials(true);

        // preflight 요청 캐시 시간 (초)
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}