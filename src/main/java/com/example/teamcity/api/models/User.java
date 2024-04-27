package com.example.teamcity.api.models;

import lombok.*;


@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String username;
    private String password;
    private String email;
    private Roles roles;
}
