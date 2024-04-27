package com.example.teamcity.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewProjectDescription {
    private Project parentProject;
    private String name;
    private String id;
    private boolean copyAllAssociatedSettings;
}
