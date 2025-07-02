package org.sopt.global.security.jwt.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sopt.domain.user.domain.User;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtPayload {
    private Map<String, Object> payload;

    public static JwtPayload from(User user) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", user.getId());
        return new JwtPayload(payload);
    }
}