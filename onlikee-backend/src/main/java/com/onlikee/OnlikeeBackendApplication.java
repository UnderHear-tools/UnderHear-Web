package com.onlikee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OnlikeeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlikeeBackendApplication.class, args);
    }

}
