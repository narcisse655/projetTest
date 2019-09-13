package net.guides.springboot2.springboot2jpacrudexample.service;

import net.guides.springboot2.springboot2jpacrudexample.model.Materiel;
import net.guides.springboot2.springboot2jpacrudexample.model.Role;
import net.guides.springboot2.springboot2jpacrudexample.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    public Users saveUser(Users user);
    public Role saveRole(Role role);
    public void addRoleToUsers(String username, String roleName);
    public Users findUsersByUsername(String username);
    public Role findRoleByRoleName(String roleName);
    public void moveRoleToUsers(String username, String roleName);
    public Page<Users> getUsers(int page);
}
