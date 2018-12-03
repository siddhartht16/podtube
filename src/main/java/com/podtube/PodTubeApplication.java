package com.podtube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PodTubeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PodTubeApplication.class, args);
	}
}
