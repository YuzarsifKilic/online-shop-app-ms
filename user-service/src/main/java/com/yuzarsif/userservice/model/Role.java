package com.yuzarsif.userservice.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_USER, ROLE_ADMIN, ROLE_CUSTOMER, ROLE_SELLER;

    @Override
    public String getAuthority() {
        return name();
    }
}
