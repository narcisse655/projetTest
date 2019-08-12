package net.guides.springboot2.springboot2jpacrudexample.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.guides.springboot2.springboot2jpacrudexample.model.Users;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super();
        this.authenticationManager = authenticationManager;
    }

    /**
     * Dans ce cas les données postées sont envoyées au format JSON. Quand les
     * données sont envoyées au format 3W url faut faire: 
     * username = request.getParameter("username") et
     *  password = request.getParameter("password")
     */
    // ObjectMapper: permet de prendre un objet JSON et le stocker dans un object java
    /*
     * request.getInputStream(): contenu de la requete. on va le démander de le
     * désérialiser dans un objet de type Users
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        Users users = null;
        try {
            users = new ObjectMapper().readValue(request.getInputStream(), Users.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("Username: "+users.getUsername());
        System.out.println("Password: "+users.getPassword());
        
        return authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));
    }

    //ii on va générer le token
    //User de spring contient les info sur l'utilisateur qui est authentifié
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
     FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User springUser = (User) authResult.getPrincipal();
        String jwt = Jwts.builder()
        .setSubject(springUser.getUsername())
        .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET)
        .claim("roles", springUser.getAuthorities())
        .compact();
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+jwt);

    }

}