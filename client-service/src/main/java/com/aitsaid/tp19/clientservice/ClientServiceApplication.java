package com.aitsaid.tp19.clientservice;

import com.aitsaid.tp19.clientservice.entities.Client;
import com.aitsaid.tp19.clientservice.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class ClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ClientRepository clientRepository) {
        return args -> {
            clientRepository.save(new Client(null, "Radouane ait said", 25F));
            clientRepository.save(new Client(null, "Chorok ait said", 31F));
            clientRepository.save(new Client(null, "Soufyane ait said", 34F));
        };
    }

}
