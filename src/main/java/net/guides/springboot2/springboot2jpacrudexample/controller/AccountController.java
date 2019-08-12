package net.guides.springboot2.springboot2jpacrudexample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.guides.springboot2.springboot2jpacrudexample.model.Role;
import net.guides.springboot2.springboot2jpacrudexample.model.Users;
import net.guides.springboot2.springboot2jpacrudexample.service.AccountService;

@RestController 
@RequestMapping("/api/v1")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public Users register(@RequestBody RegisterForm userForm){
        if (!userForm.getPassword().equals(userForm.getRepassword())) 
        throw new RuntimeException("You must confirm your password");
        Users usersExist = accountService.findUsersByUsername(userForm.getUsername());
        if (usersExist != null)  
        throw new RuntimeException("This user already exists");
        Users users = new Users();
        users.setUsername(userForm.getUsername());
        users.setPassword(userForm.getPassword());
        accountService.saveUser(users);
        accountService.addRoleToUsers(users.getUsername(), "USER");
        return users;
    }

    @PostMapping("/users/role/add")
    public Users addRoleToUsers(@RequestParam(value ="u") String username,
    @RequestParam(value ="roleName") String roleName) {
        Users users = accountService.findUsersByUsername(username);
        for(Role r : users.getRoleList()){
            if (r.getRoleName().equals(roleName))
            throw new RuntimeException("This role is already added to this user");
        }
        accountService.addRoleToUsers(username, roleName);
        return users;
    }

    @PostMapping("users/role/move")
    public Users moveRoleToUsers(@RequestParam(value ="u") String username, 
    @RequestParam(value ="roleName") String roleName){
        Users users = accountService.findUsersByUsername(username);
        accountService.moveRoleToUsers(username, roleName);
        return users;
    }

    @GetMapping("/users")
    public Users getUsers(@RequestParam(value="u") String username){
        return accountService.findUsersByUsername(username);
    }
    
    
}
