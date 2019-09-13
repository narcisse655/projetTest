package net.guides.springboot2.springboot2jpacrudexample.service;

import net.guides.springboot2.springboot2jpacrudexample.model.Materiel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.guides.springboot2.springboot2jpacrudexample.model.Role;
import net.guides.springboot2.springboot2jpacrudexample.model.Users;
import net.guides.springboot2.springboot2jpacrudexample.repository.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{

    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Users saveUser(Users users) {
        String hashPwd = bcryptPasswordEncoder.encode(users.getPassword());
        users.setPassword(hashPwd);
        return usersRepository.save(users);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUsers(String username, String roleName) {
        Role role = roleRepository.findByRoleName(roleName);
        Users users = usersRepository.findByUsername(username);
        users.getRoleList().add(role);

    }

    @Override
    public Users findUsersByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public void moveRoleToUsers(String username, String roleName) {
        Role role = roleRepository.findByRoleName(roleName);
        Users users = usersRepository.findByUsername(username);
        if (users.getRoleList().contains(role)) 
            users.getRoleList().remove(role);
    }

    @Override
    public Page<Users> getUsers(int page){
        return usersRepository.findAll(new PageRequest(page, 5));
    }

}
