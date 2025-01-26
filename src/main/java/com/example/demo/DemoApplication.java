package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@GetMapping("/")
	public String welcome() {
		return "HELLO FROM AUTHENTICATED ENDPOINT!";
	}

	@GetMapping("/admins")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String welcomeAdmin() {
		return "HELLO FROM ADMIN'S ENDPOINT!";
	}

}
