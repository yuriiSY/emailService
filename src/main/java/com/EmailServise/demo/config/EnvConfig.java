package com.EmailServise.demo.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import io.github.cdimascio.dotenv.Dotenv;


@Component
public class EnvConfig {
    @PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }
}
