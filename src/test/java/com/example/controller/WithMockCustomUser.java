package com.example.controller;
import java.lang.annotation.Retention;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@WithSecurityContext(
    factory = WithMockUserSecurityContextFactory.class
)
public @interface WithMockCustomUser {
    String value() default "user";

    String username() default "name";

    String[] roles() default {"NONE"};

    String password() default "password";

    String uuid() default "87559ff1-ae30-4af5-9ba2-1723bed1f706";
}