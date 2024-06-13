package ru.study.base.tgjavabot.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {

    PLACE_JOKES,
    MANAGE_JOKES,
    ALl;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
