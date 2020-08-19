package com.mc.bzly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = { "com.mc.bzly" })
@EnableTransactionManagement
@ImportResource(locations = { "dubbo-provider.xml"})
@EnableScheduling
public class BzlyService {
    public static void main( String[] args ) {
        SpringApplication.run(BzlyService.class, args);
    }
}
