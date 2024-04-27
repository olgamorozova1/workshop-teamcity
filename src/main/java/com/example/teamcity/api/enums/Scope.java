package com.example.teamcity.api.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Scope {
    G("g"),
    P("p:");

    private String text;

    public String getText() {
        return text;
    }
}
