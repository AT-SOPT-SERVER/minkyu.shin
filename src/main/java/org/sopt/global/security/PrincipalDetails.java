package org.sopt.global.security;

import org.sopt.domain.user.domain.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.user.domain.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PrincipalDetails implements UserDetails {

    private final User user;

    public static PrincipalDetails from(User user) {
        return new PrincipalDetails(user);
    }

    public Long getUserId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority(user.getUserRole().getAuthority()));
        return auth;
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // 소셜 로그인 시 null 가능
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 기능 미사용
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getStatus() != UserStatus.SUSPENDED; // 정지 상태 체크
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 기능 미사용
    }

    @Override
    public boolean isEnabled() {
        return user.isActive(); // ACTIVE 상태만 활성화
    }
}