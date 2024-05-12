package com.example.teamcity.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@EqualsAndHashCode
public class Project {
    private String id;
    private String name;
    private String parentProjectId;
    private String locator;
    private Boolean copyAllAssociatedSettings;
}
