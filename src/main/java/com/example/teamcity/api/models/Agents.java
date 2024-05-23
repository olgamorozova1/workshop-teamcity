package com.example.teamcity.api.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Agents {
    private int count;
    private String href;
    private List<Agent> agent;
}
