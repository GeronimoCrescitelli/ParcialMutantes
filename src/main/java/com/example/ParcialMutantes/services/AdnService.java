package com.example.ParcialMutantes.services;

import com.example.ParcialMutantes.repositories.AdnRepository;
import com.example.ParcialMutantes.entities.Adn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AdnService {

    private final AdnRepository adnRepository;
    private static final int SEQUENCE_LENGTH = 4;

    @Autowired
    public AdnService(AdnRepository adnRepository) {
        this.adnRepository = adnRepository;
    }

    public static boolean isMutant(String[] adn) {
        int n = adn.length;
        int sequenceCount = 0;

        // Verificamos filas
        sequenceCount += checkRows(adn, n);
        if (sequenceCount > 1) return true;

        // Verificamos columnas
        sequenceCount += checkColumns(adn, n);
        if (sequenceCount > 1) return true;

        // Verificamos diagonales
        sequenceCount += checkDiagonals(adn, n);
        if (sequenceCount > 1) return true;
        //return sequenceCount > 1;
        else return false;
    }

    private static int checkRows(String[] adn, int n) {
        int sequenceCount = 0;

        for (int i = 0; i < n; i++) {
            int count = 1;
            for (int j = 1; j < n; j++) {
                if (adn[i].charAt(j) == adn[i].charAt(j - 1)) {
                    count++;
                    if (count == SEQUENCE_LENGTH) {
                        sequenceCount++;
                        if (sequenceCount > 1) return sequenceCount;
                    }
                } else {
                    count = 1;
                }
            }
        }
        return sequenceCount;
    }

    private static int checkColumns(String[] adn, int n) {
        int sequenceCount = 0;

        for (int j = 0; j < n; j++) {
            int count = 1;
            for (int i = 1; i < n; i++) {
                if (adn[i].charAt(j) == adn[i - 1].charAt(j)) {
                    count++;
                    if (count == SEQUENCE_LENGTH) {
                        sequenceCount++;
                        if (sequenceCount > 1) return sequenceCount;
                    }
                } else {
                    count = 1;
                }
            }
        }
        return sequenceCount;
    }

    private static int checkDiagonals(String[] adn, int n) {
        int sequenceCount = 0;

        // Diagonales de izquierda (arriba) a derecha (abajo)
        for (int i = 0; i <= n - SEQUENCE_LENGTH; i++) {
            for (int j = 0; j <= n - SEQUENCE_LENGTH; j++) {
                if (checkDiagonal(adn, i, j, 1, 1, n)) {
                    sequenceCount++;
                    if (sequenceCount > 1) return sequenceCount; // Early exit
                }
            }
        }

        // Diagonales de derecha (arriba) a izquierda (abajo)
        for (int i = 0; i <= n - SEQUENCE_LENGTH; i++) {
            for (int j = SEQUENCE_LENGTH - 1; j < n; j++) {
                if (checkDiagonal(adn, i, j, 1, -1, n)) {
                    sequenceCount++;
                    if (sequenceCount > 1) return sequenceCount;
                }
            }
        }
        return sequenceCount;
    }

    private static boolean checkDiagonal(String[] adn, int x, int y, int dx, int dy, int n) {
        char first = adn[x].charAt(y);
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (x + i * dx >= n || y + i * dy >= n || y + i * dy < 0 || adn[x + i * dx].charAt(y + i * dy) != first) {
                return false;
            }
        }
        return true;
    }

    public boolean analyzeAdn(String[] adn) {
        String adnSequence = String.join(",", adn);

        // Verificamos si el ADN ya existe en la base de datos
        Optional<Adn> existingAdn = adnRepository.findByAdn(adnSequence);
        if (existingAdn.isPresent()) {
            // Si el ADN ya fue analizado, retornamos el resultado
            return existingAdn.get().isMutant();
        }

        // Determinamos si el ADN es mutante y lo guardamos en la base de datos
        boolean isMutant = isMutant(adn);
        Adn adnEntity = Adn.builder()
                .adn(adnSequence)
                .isMutant(isMutant)
                .build();
        adnRepository.save(adnEntity);

        return isMutant(adn);
    }
}