package com.example.ParcialMutantes.services;

import com.example.ParcialMutantes.dto.StatsResponse;
import com.example.ParcialMutantes.repositories.AdnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final AdnRepository adnRepository;

    @Autowired
    public StatsService(AdnRepository adnRepository) {
        this.adnRepository = adnRepository;
    }

    public StatsResponse getStats() {
        long countMutantAdn = adnRepository.countByIsMutant(true);
        long countHumanAdn = adnRepository.countByIsMutant(false);
        double ratio = countHumanAdn == 0 ? 0 : (double) countMutantAdn / countHumanAdn;
        return new StatsResponse(countMutantAdn, countHumanAdn, ratio);
    }
}