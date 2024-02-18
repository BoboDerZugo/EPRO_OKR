package com.example.service;

import org.springframework.security.core.context.SecurityContextHolder;
import com.example.model.BusinessUnit;
import com.example.model.Company;
import com.example.model.OKRSet;
import com.example.model.User;
import com.example.service.MyUserDetailsService.MyUserPrincipal;

public class AuthorizationService {


    /**
     * Checks if the user is authorized to access the business unit.
     *
     * @param company      The company.
     * @param businessUnit The business unit.
     * @return True if the user is authorized, false otherwise.
     */
    public static boolean isAuthorized(Company company, BusinessUnit businessUnit, OKRSet okrSet) {
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
            //Check if Bu is part of the company
            if(!company.getBusinessUnits().contains(businessUnit)) {
                return false;
            }
            //Check if user is part of the business unit
            if (businessUnit.getUnits().stream().map(unit -> unit.getEmployeeSet().contains(user)).findAny()
                    .orElse(false)) {
                //check if okrSet is part of the business unit
                if(okrSet != null){
                    return businessUnit.getOkrSets().contains(okrSet);
                }
                return true;
            }
        }

        return false;
    }
}
