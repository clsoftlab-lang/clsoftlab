package com.example.clsoftlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class ClsoftlabApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClsoftlabApplication.class, args);
	}

}
