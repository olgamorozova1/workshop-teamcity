package com.example.teamcity.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import static com.example.teamcity.api.constants.Endpoints.USER_BY_USERNAME_ENDPOINT;


@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Deletable {
    private String username;
    private String password;
    private String email;
    private Roles roles;

    @Override
    public String getEndpointToDelete() {
        return USER_BY_USERNAME_ENDPOINT;
    }

    @Override
    public String getIdToDelete() {
        return username;
    }
}
