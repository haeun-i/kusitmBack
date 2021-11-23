package com.kusitms.kusitms5.dto;

import com.kusitms.kusitms5.domain.User;

public class UserProfile {
    private final String oauthId;
    private final String name;
    private final String email;

    public UserProfile(String oauthId, String name, String email) {
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
    }

    public User toUser() {
        return new User(name, email);
    }

    public String getOauthId() {
        return oauthId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}