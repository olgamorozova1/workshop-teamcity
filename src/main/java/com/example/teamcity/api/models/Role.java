package com.example.teamcity.api.models;

import com.example.teamcity.api.generators.annotations.DefaultValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role {
    @DefaultValue("SYSTEM_ADMIN")
    private String roleId;
    @DefaultValue("G")
    private String scope;
    private String href;
}
