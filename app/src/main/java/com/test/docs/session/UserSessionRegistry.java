package com.test.docs.session;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.test.docs.entity.LoginUser;

@Component
public class UserSessionRegistry {

    private static final Map<String, String> sessions = new HashMap<>();

    public String createSession(final LoginUser user) {
        if (user == null) {
            throw new RuntimeException("User not specified");
        } else {
            final String sessionId = new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)));
            sessions.put(user.getLoginId(), sessionId);
            return sessions.get(user.getLoginId());
        }
    }

    public String getUserNameFromSession(final String sessionId) {

        for (Map.Entry<String,String> entry : sessions.entrySet()) {
            if (entry.getValue().equals(sessionId))
                return entry.getKey();
        }

        return null;
    }

    public String getSessionId(final String loginUser) {
       return sessions.get(loginUser);
    }

    public void removeSession(String loginUser) {
        sessions.remove(loginUser);
    }
}