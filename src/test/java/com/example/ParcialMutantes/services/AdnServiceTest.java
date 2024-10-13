package com.example.ParcialMutantes.services;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AdnServiceTest {
    @Autowired
    private AdnService AdnService;
    //Chequea coincidencias en filas
    @Test
    public void testRows(){
        String[] adn = {
                "CCCCTA",
                "TGCAGT",
                "GCTTCC",
                "CCCCTG",
                "GTAGTC",
                "AGTCAC"
        };
        assertTrue(AdnService.isMutant(adn), "Deberia dar como resultado mutante");
    }

    //Chequea coincidencias en columnas
    @Test
    public void testColumns(){
        String[] adn = {
                "AGAATG",
                "TGCAGT",
                "GCTTCC",
                "GTCCTC",
                "GTAGTC",
                "GGTCAC"
        };
        boolean result = AdnService.isMutant(adn);
        assertTrue(AdnService.isMutant(adn), "Deberia dar como resultado mutante");    }

    //Chequea ambas diagonales principales
    @Test
    public void testMainDiagonals(){
        String[] adn = {
                "AGAATG",
                "TACAGT",
                "GCAGCC",
                "TTGATG",
                "GTAGTC",
                "AGTCAA"
        };
        assertTrue(AdnService.isMutant(adn), "Deberia dar como resultado mutante");    }

    //Chequea diagonales derecha ↘
    @Test
    public void testDiagonalRight(){
        String[] adn = {
                "ATAATG",
                "GTTAGT",
                "GGCTCG",
                "TTGATG",
                "GTAGTC",
                "AGTCAA"
        };
        assertTrue(AdnService.isMutant(adn), "Deberia dar como resultado mutante");    }

    //Chequea diagonales izquierda ↙
    @Test
    public void testDiagonalLeft(){
        String[] adn = {
                "ATAATG",
                "GTATGA",
                "GCTTAG",
                "TTTAGG",
                "GTAGTC",
                "AGTCAA"
        };
        assertTrue(AdnService.isMutant(adn), "Deberia dar como resultado mutante");    }

    //Chequea filas y columnas
    @Test
    public void testRowsCols(){
        String[] adn = {
                "CGATCA",
                "GATGCT",
                "TCCCCT",
                "TACAGT",
                "GTAACT",
                "ACCGTA"
        };
        assertTrue(AdnService.isMutant(adn), "Deberia dar como resultado mutante");    }

    //Chequea coincidencias en todas las direcciones
    @Test
    public void testAllDirections(){
        String[] adn = {
                "GGTGTG",
                "TCGCCG",
                "CCAAAA",
                "ACTGAT",
                "GCCAGC",
                "CTACTA"
        };
        assertTrue(AdnService.isMutant(adn), "Deberia dar como resultado mutante");    }

    //Chequea coincidencia no mutante
    @Test
    public void testNonMutant(){
        String[] adn = {
                "ATCGAT",
                "CTCTTG",
                "CAAGGC",
                "GGTATT",
                "ATCGAT",
                "AAGTCC"
        };
        assertFalse(AdnService.isMutant(adn), "Deberia dar como resultado NO mutante");    }

}
