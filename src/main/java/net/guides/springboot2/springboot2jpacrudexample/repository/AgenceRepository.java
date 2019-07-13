package net.guides.springboot2.springboot2jpacrudexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import net.guides.springboot2.springboot2jpacrudexample.model.*;

@Repository
public interface AgenceRepository extends JpaRepository<Agence, Integer>{

    @Query("SELECT a FROM Agence a WHERE a.numAgence =:agenceId")
    Agence chercherUnContratParId(@Param("agenceId") int agenceId);
}
