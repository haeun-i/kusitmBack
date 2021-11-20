package com.kusitms.kusitms5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class Kusitms5Application {

	public static void main(String[] args) {
		SpringApplication.run(Kusitms5Application.class, args);
	}
}
