package com.aitsaid.tp19.voitureservice.repositories;

import com.aitsaid.tp19.voitureservice.entities.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 **/
@Repository
public interface VoitureRepository extends JpaRepository<Voiture, Long> {

    @Query("SELECT v FROM Voiture v WHERE v.id_client = :id")
    List<Voiture> findByClientId(Long id);
}
