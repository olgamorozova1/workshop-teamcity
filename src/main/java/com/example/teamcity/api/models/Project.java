package com.example.teamcity.api.models;

import com.example.teamcity.api.generators.annotations.DefaultValue;
import com.example.teamcity.api.generators.annotations.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

import static com.example.teamcity.api.constants.Endpoints.PROJECT_ENDPOINT_BY_ID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@EqualsAndHashCode
public class Project implements Deletable {
    @Optional
    private String id;
    private String name;
    @Optional
    private String parentProjectId;
    @DefaultValue("_Root")
    private String locator;
    private Boolean copyAllAssociatedSettings;

    @Override
    public String getEndpointToDelete() {
        return PROJECT_ENDPOINT_BY_ID;
    }

    @Override
    public String getIdToDelete() {
        return id;
    }
}
