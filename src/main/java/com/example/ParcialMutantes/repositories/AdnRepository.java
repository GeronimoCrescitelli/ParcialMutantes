package com.example.ParcialMutantes.repositories;

import com.example.ParcialMutantes.entities.Adn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AdnRepository extends JpaRepository<Adn, Long> {
    Optional<Adn> findByAdn(String adnSequence);

    long countByIsMutant(boolean isMutant);
}