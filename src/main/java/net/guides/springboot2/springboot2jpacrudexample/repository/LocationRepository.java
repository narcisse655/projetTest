package net.guides.springboot2.springboot2jpacrudexample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import net.guides.springboot2.springboot2jpacrudexample.model.*;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("SELECT l FROM Location l WHERE l.contrat.numContrat =:contratId")
    List<Location> findLocationById(@Param("contratId") int contratId);

    @Query("SELECT l FROM Location l WHERE l.contrat.numContrat=:contratId AND l.materiel.refMateriel=:materielId")
    Location checkIfCoupleIdExist(@Param("contratId") int contratId, @Param("materielId") int materielId);
}
