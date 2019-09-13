package net.guides.springboot2.springboot2jpacrudexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.guides.springboot2.springboot2jpacrudexample.model.*;

import java.util.List;

@Repository
public interface MaterielRepository extends JpaRepository<Materiel, Integer>{

    public List<Materiel> findByFileName(String fileName);

}
