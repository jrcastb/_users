package com.bci

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = [
        "com.bci.application.service",
        "com.bci.infrastructure.output.adapter",
        "com.bci.infrastructure.output.adapter.mapper",
        "com.bci.application.service.mapper",
        "com.bci.infrastructure.output.repository"
])
class Main {

    static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}