package com.mc.bzly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ImportResource({ "classpath:dubbo-consumer.xml" })
public class BzlyApi {
	public static void main(String[] args) { 
		SpringApplication.run(BzlyApi.class, args);
	}
}
