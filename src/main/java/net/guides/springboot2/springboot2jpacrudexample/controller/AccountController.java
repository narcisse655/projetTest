package net.guides.springboot2.springboot2jpacrudexample.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.guides.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
import net.guides.springboot2.springboot2jpacrudexample.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.guides.springboot2.springboot2jpacrudexample.model.Role;
import net.guides.springboot2.springboot2jpacrudexample.model.Users;
import net.guides.springboot2.springboot2jpacrudexample.service.AccountService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController 
@RequestMapping("/api/v1")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    UsersRepository usersRepository;

    /*@PostMapping("/register")
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
    }*/

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterForm userForm){
        if (!userForm.getPassword().equals(userForm.getRepassword()))
            throw new RuntimeException("You must confirm your password");
        Users userExist = accountService.findUsersByUsername(userForm.getUsername());
        if (userExist != null)
            throw new RuntimeException("This user already exists");
        Users user = new Users();
        user.setUsername(userForm.getUsername());
        user.setPassword(userForm.getPassword());
        user.setActive(true);
        Users usersAdded = accountService.saveUser(user);
        if (usersAdded == null)
            ResponseEntity.noContent().build();
        accountService.addRoleToUsers(user.getUsername(), "USER");
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usersAdded.getIdUsers())
                .toUri();
        return ResponseEntity.created(location).build();
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

    @GetMapping("/users/list")
    public Page<Users> getUsers(@RequestParam(defaultValue = "0") int page){
        return accountService.getUsers(page);
    }

    @PutMapping("/users/enable/{id}")
    public ResponseEntity<Users> enableUsers(@PathVariable(value="id") int id) throws ResourceNotFoundException{
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        user.setActive(true);
        final Users userEnable = usersRepository.save(user);
        return ResponseEntity.ok(userEnable);
    }

    @PutMapping("/users/disable/{id}")
    public ResponseEntity<Users> disableUsers(@PathVariable(value="id") int id) throws ResourceNotFoundException{
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        user.setActive(false);
        final Users userDisable = usersRepository.save(user);
        return ResponseEntity.ok(userDisable);
    }

    @GetMapping("/users/{username}")
    public Users getUsersEnable(@PathVariable(value="username") String username){
        return accountService.findUsersByUsername(username);
    }

}
