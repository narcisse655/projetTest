package net.guides.springboot2.springboot2jpacrudexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.guides.springboot2.springboot2jpacrudexample.model.Role;
import net.guides.springboot2.springboot2jpacrudexample.model.Users;
import net.guides.springboot2.springboot2jpacrudexample.property.FileStorageProperties;
import net.guides.springboot2.springboot2jpacrudexample.service.AccountService;

@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class
})
public class Springboot2JpaCrudExampleApplication implements CommandLineRunner{

	@Autowired
	private AccountService accountService;

	public static void main(String[] args) {
		SpringApplication.run(Springboot2JpaCrudExampleApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder getBCPE(){
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		/* accountService.saveUser(new Users(null, "admin", "1234", null));
		accountService.saveUser(new Users(null, "user", "1234", null));
		accountService.saveRole(new Role(null, "ADMIN"));
		accountService.saveRole(new Role(null, "USER"));
		accountService.addRoleToUsers("admin", "ADMIN");
		accountService.addRoleToUsers("admin", "USER");
		accountService.addRoleToUsers("user", "USER"); */

	}



}
