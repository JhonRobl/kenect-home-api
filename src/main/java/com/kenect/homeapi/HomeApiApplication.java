package com.kenect.homeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class HomeApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(HomeApiApplication.class, args);
	}

}
