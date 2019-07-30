package net.guides.springboot2.springboot2jpacrudexample.service;

import net.guides.springboot2.springboot2jpacrudexample.model.Role;
import net.guides.springboot2.springboot2jpacrudexample.model.Users;

public interface AccountService {
    public Users saveUser(Users user);
    public Role saveRole(Role role);
    public void addRoleToUsers(String username, String roleName);
    public Users findUsersByUsername(String username);
    public Role findRoleByRoleName(String roleName);
    public void moveRoleToUsers(String username, String roleName);
}
