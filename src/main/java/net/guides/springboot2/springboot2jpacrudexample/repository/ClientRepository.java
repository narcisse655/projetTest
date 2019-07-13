package net.guides.springboot2.springboot2jpacrudexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import net.guides.springboot2.springboot2jpacrudexample.model.*;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{
    @Query("SELECT c FROM Client c WHERE c.numClient =:clientId")
    Client chercherUnClientParId(@Param("clientId") int clientId);
}
