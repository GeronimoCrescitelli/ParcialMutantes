package com.example.ParcialMutantes.dto;

import com.example.ParcialMutantes.validators.ValidAdn;
import lombok.*;

@Getter
@Setter
public class AdnRequest {
    @ValidAdn
    private String[] adn;
}