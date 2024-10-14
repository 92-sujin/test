package com.example.dkbmcsampleproject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories(basePackages = "com.example.dkbmcsampleproject1.repository")
public class DkbmcSampleProject1Application {

	public static void main(String[] args) {
		SpringApplication.run(DkbmcSampleProject1Application.class, args);
	}

}
