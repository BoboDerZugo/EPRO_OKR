package com.example.controller;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import com.example.model.MyUserPrincipal;
import com.example.model.User;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        User user = new User(customUser.username(), customUser.password(), customUser.roles()[0]);
        user.setUuid(UUID.fromString(customUser.uuid()));
        MyUserPrincipal principal = new MyUserPrincipal(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, customUser.password(),
                principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }

}
