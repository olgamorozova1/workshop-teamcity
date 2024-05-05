package com.example.teamcity.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class NewProjectDescription {
    private Project parentProject;
    private String name;
    private String id;
    private Boolean copyAllAssociatedSettings;
}
