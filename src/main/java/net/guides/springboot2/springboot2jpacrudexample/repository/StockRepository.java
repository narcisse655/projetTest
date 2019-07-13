package net.guides.springboot2.springboot2jpacrudexample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.guides.springboot2.springboot2jpacrudexample.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("SELECT s FROM Stock s WHERE s.agence.numAgence =:agenceId")
    List<Stock> findStockById(@Param("agenceId") int agenceId);

    @Query("SELECT s FROM Stock s WHERE s.agence.numAgence=:agenceId AND s.materiel.refMateriel=:materielId")
    Stock checkIfCoupleIdExist(@Param("agenceId") int agenceId, @Param("materielId") int materielId);
   
}
