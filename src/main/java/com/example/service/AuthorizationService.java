package com.example.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.model.BusinessUnit;
import com.example.model.Company;
import com.example.model.User;
import com.example.service.MyUserDetailsService.MyUserPrincipal;

public class AuthorizationService {
    public static boolean isAuthorized(Company company, BusinessUnit businessUnit) {
        // get authenticated user
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof MyUserPrincipal)) {
            return false;
        }
        MyUserDetailsService.MyUserPrincipal u = (MyUserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = u.getUser();
        if (company == null) {
            return false;
        }
        if (user.getRole().equals("CO_ADMIN")) {
            return true;
        }
        if (businessUnit == null) {
            return false;
        }
        if (user.getRole().equals("BU_ADMIN")) {
            if (businessUnit.getUnits().stream().map(unit -> unit.getEmployeeSet().contains(user)).findAny()
                    .orElse(false)) {
                return true;
            }
        }
        return false;
    }
}
