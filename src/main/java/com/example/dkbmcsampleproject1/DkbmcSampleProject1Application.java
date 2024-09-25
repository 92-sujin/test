package com.example.dkbmcsampleproject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DkbmcSampleProject1Application {

	public static void main(String[] args) {
		SpringApplication.run(DkbmcSampleProject1Application.class, args);
	}

}
