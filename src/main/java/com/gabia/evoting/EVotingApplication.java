package com.gabia.evoting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class EVotingApplication {

	public static void main(String[] args) {

		SpringApplication.run(EVotingApplication.class, args);

	}

}
