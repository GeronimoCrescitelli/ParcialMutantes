package com.example.ParcialMutantes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StatsResponse {

    @JsonProperty("count_mutant_adn")
    private long countMutantAdn;

    @JsonProperty("count_human_adn")
    private long countHumanAdn;

    private double ratio;
}