package net.guides.springboot2.springboot2jpacrudexample.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
         auth.inMemoryAuthentication()
        .withUser("admin").password("{noop}1234").roles("ADMIN", "USER")
        .and()
        .withUser("user").password("{noop}1234").roles("USER");
    } */
    /* @Bean
    public UserDetailsService userDetailsService(){
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("admin").password("1234").roles("ADMIN","USER").build());
        manager.createUser(users.username("user").password("1234").roles("USER").build());
        return manager;
    }
    */
    @Autowired
    private UserDetailsService userDetailsService;//authentification basée sur UserDetailsService
    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bcryptPasswordEncoder);
    }
        
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http.csrf().disable();/*Ce n'est plus la peine de use ce sychronize token */
        http.formLogin();
        http.authorizeRequests().antMatchers("/login/**", "/api/v1/register/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/materiels/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/users/role/move/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/users/role/add/**").hasAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
    }

    
}

