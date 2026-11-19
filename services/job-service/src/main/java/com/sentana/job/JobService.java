package com.sentana.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class JobService {

	public static void main(String[] args) {
		SpringApplication.run(JobService.class, args);
	}

}
