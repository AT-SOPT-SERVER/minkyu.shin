package org.sopt.global.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.sopt.global.jwt.JwtResolver;
import org.sopt.global.util.HeaderTokenExtractor;
import org.sopt.global.security.PrincipalDetailsService;
import org.sopt.global.security.SecurityConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HeaderTokenExtractor headerTokenExtractor;
    private final JwtResolver jwtResolver;
    private final PrincipalDetailsService principalDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        List<AntPathRequestMatcher> skipList = new ArrayList<>();
        for (String pattern : SecurityConstants.PUBLIC_URLS) {
            skipList.add(new AntPathRequestMatcher(pattern));
        }
        for (String pattern : SecurityConstants.PUBLIC_API_URLS) {
            skipList.add(new AntPathRequestMatcher(pattern));
        }

        OrRequestMatcher orRequestMatcher = new OrRequestMatcher(new ArrayList<>(skipList));
        return orRequestMatcher.matches(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = headerTokenExtractor.extractAccessToken(request);
        validateAccessToken(accessToken);

        try {
            Long userId = jwtResolver.getUserIdFromAccessToken(accessToken);
            UserDetails userDetails = principalDetailsService.loadUserById(userId);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("인증된 사용자 아이디: {}", userId);
        } catch (UsernameNotFoundException e) {
            throw new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        filterChain.doFilter(request, response);
    }

    private void validateAccessToken(String accessToken) {
        if (!StringUtils.hasText(accessToken)) {
            throw new BusinessException(ErrorCode.INVALID_ACCESS_TOKEN_EXCEPTION);
        }

        if (!jwtResolver.validateAccessToken(accessToken)) {
            log.warn("유효하지 않은 Access Token: {}", accessToken);
            throw new BusinessException(ErrorCode.INVALID_ACCESS_TOKEN_EXCEPTION);
        }
    }


}