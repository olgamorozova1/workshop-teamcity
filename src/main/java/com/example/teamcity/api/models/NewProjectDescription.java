package com.example.teamcity.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

import static com.example.teamcity.api.constants.Endpoints.PROJECT_ENDPOINT_BY_ID;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewProjectDescription implements Deletable {
    private Project parentProject;
    private String name;
    private String id;
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
