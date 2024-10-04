package com.nagornov.scientist;

import com.nagornov.scientist.config.properties.CsrfProperties;
import com.nagornov.scientist.config.properties.JwtProperties;
import com.nagornov.scientist.config.properties.ServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ServiceProperties.class, JwtProperties.class, CsrfProperties.class})
public class ScientistApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScientistApplication.class, args);
	}

}
