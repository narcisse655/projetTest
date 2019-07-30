package net.guides.springboot2.springboot2jpacrudexample.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.guides.springboot2.springboot2jpacrudexample.model.Users;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = accountService.findUsersByUsername(username);
        if (users == null) throw new UsernameNotFoundException(username);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        users.getRoleList().forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        });
        return new User(users.getUsername(), users.getPassword(), authorities);
    }

   
    
}
