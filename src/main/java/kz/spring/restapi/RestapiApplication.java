package kz.spring.restapi;

import kz.spring.restapi.model.Role;
import kz.spring.restapi.model.Users;
import kz.spring.restapi.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@SpringBootApplication
@EnableAsync
public class RestapiApplication{


	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/*@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
			userService.saveRole(new Role(null,"ROLE_USER"));
			userService.saveRole(new Role(null,"ROLE_MANAGER"));
			userService.saveRole(new Role(null,"ROLE_ADMIN"));
			userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));

			userService.saveUser(new Users(null,"Admin", "admin@gmail.com","123", new HashSet<>()));
			userService.saveUser(new Users(null,"Super Admin", "sadmin@gmail.com","123", new HashSet<>()));
			userService.saveUser(new Users(null,"Manager", "manager@gmail.com","123", new HashSet<>()));
			userService.saveUser(new Users(null,"User", "user@gmail.com","123", new HashSet<>()));

			userService.addRoleToUser("admin@gmail.com", "ROLE_ADMIN");
			userService.addRoleToUser("admin@gmail.com", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("admin@gmail.com", "ROLE_USER");
			userService.addRoleToUser("sadmin@gmail.com", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("manager@gmail.com", "ROLE_MANAGER");
			userService.addRoleToUser("user@gmail.com", "ROLE_USER");
		};
	}*/

}
