package net.guides.springboot2.springboot2jpacrudexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import net.guides.springboot2.springboot2jpacrudexample.model.*;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>{
    @Query("SELECT u from Users u where u.username=:username")
    public Users findByUsername(@Param("username") String username);
}
