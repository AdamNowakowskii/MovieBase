package com.application.movieBase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MovieBaseApplication {

	public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MovieBaseApplication.class, args);
	}

}
