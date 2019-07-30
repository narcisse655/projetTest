package net.guides.springboot2.springboot2jpacrudexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import net.guides.springboot2.springboot2jpacrudexample.model.*;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
    
    @Query("SELECT r from Role r where r.roleName=:roleName")
    Role findByRoleName(@Param("roleName") String roleName);
}
