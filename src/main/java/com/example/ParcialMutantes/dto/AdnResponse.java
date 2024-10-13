package com.example.ParcialMutantes.dto;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class AdnResponse {
    private boolean isMutant;

    public boolean isMutant() {
        return isMutant;
    }
}

