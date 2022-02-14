package com.hyunsoo.detectapp;

import com.hyunsoo.detectapp.config.DetectProperties;
import com.hyunsoo.detectapp.config.KafkaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableConfigurationProperties(value = {
        DetectProperties.class,
        KafkaProperties.class
})
@SpringBootApplication
public class DetectAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DetectAppApplication.class, args);
    }

}
