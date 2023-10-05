package com.test.docs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.docs.entity.ApiResponse;
import com.test.docs.entity.AuthToken;
import com.test.docs.entity.LoginUser;
import com.test.docs.session.UserSessionRegistry;

@CrossOrigin("*")
@RestController
@RequestMapping("/usersession")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserSessionRegistry sessionRegistry;

    @PostMapping("/signin")
    public ApiResponse<AuthToken> login(@RequestBody LoginUser loginUser) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getLoginId(), loginUser.getLoginPassword()));
        String sessionId = sessionRegistry.createSession(loginUser);
        return new ApiResponse<>(200, "success", sessionId);
    }

    @PostMapping("/signout")
    public void logout(@RequestHeader(name = "userId") String loginUser) {
         sessionRegistry.removeSession(loginUser);
    }
}
