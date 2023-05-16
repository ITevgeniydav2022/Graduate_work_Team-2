package com.example.graduate_work_team2.dto;

import org.springframework.security.core.GrantedAuthority;
/**
 *
 * Enum Role - типы пользователя
 */

public enum Role implements GrantedAuthority {
    USER, ADMIN;
    @Override
    public String getAuthority() {
        return name();
    }

}