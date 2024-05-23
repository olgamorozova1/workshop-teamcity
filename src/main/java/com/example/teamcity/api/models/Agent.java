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
@EqualsAndHashCode
@Builder
public class Agent {
    private int id;
    private String name;
    private int typeId;
    private Boolean status;
}
