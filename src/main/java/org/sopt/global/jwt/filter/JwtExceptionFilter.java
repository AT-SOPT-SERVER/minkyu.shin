package org.sopt.global.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.global.dto.ApiResponse;
import org.sopt.global.exception.ErrorCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.error("JWT Token expired: {}", e.getMessage());
            setErrorResponse(response, ErrorCode.EXPIRED_ACCESS_TOKEN_EXCEPTION,
                    ErrorCode.EXPIRED_ACCESS_TOKEN_EXCEPTION.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT Token: {}", e.getMessage());
            setErrorResponse(response, ErrorCode.INVALID_ACCESS_TOKEN_EXCEPTION,
                    ErrorCode.INVALID_ACCESS_TOKEN_EXCEPTION.getMessage());
        } catch (SignatureException e) {
            log.error("JWT signature does not match: {}", e.getMessage());
            setErrorResponse(response, ErrorCode.INVALID_ACCESS_TOKEN_EXCEPTION,
                    ErrorCode.INVALID_ACCESS_TOKEN_EXCEPTION.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT Token: {}", e.getMessage());
            setErrorResponse(response, ErrorCode.UNSUPPORTED_JWT_TOKEN_EXCEPTION,
                    ErrorCode.UNSUPPORTED_JWT_TOKEN_EXCEPTION.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            setErrorResponse(response, ErrorCode.INVALID_ACCESS_TOKEN_EXCEPTION,
                    ErrorCode.INVALID_ACCESS_TOKEN_EXCEPTION.getMessage());
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode, String message)
            throws IOException {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = objectMapper.writeValueAsString(
                ApiResponse.of(errorCode, message)
        );
        response.getWriter().write(jsonResponse);
    }
}