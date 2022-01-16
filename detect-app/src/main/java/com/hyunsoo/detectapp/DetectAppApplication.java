package com.hyunsoo.detectapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(value = {

})
@SpringBootApplication
public class DetectAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DetectAppApplication.class, args);
    }

}
