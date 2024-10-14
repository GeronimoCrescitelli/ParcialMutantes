package com.example.ParcialMutantes.controllers;

import com.example.ParcialMutantes.dto.AdnResponse;
import com.example.ParcialMutantes.services.AdnService;
import com.example.ParcialMutantes.dto.AdnRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/mutant")
public class AdnController {

    private final AdnService adnService;

    public AdnController(AdnService adnService) {
        this.adnService = adnService;
    }

    @PostMapping
    public ResponseEntity<AdnResponse> checkMutant(@Valid @RequestBody AdnRequest adnRequest) {
        boolean isMutant = adnService.analizarAdn(adnRequest.getAdn());
        AdnResponse adnResponse = new AdnResponse(isMutant);
        if (isMutant) {
            return ResponseEntity.ok(adnResponse);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(adnResponse);
        }
    }

}