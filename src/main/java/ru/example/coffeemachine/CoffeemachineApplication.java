package ru.example.coffeemachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("ru.example.coffeemachine.repo")
@EntityScan("ru.example.coffeemachine.entity")
@SpringBootApplication
public class CoffeemachineApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoffeemachineApplication.class, args);
    }

}
