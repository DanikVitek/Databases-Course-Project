package com.danikvitek.MCPluginMarketplace.data.model.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    user, moderator, admin;

    @Override
    public String getAuthority() {
        return toString();
    }
}
