package com.example.customerservice;

import com.example.customerservice.entitie.Customer;
import com.example.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.List;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(CustomerRepository customerRepository, RepositoryRestConfiguration restConfiguration){
        return args -> {
            restConfiguration.exposeIdsFor(Customer.class);
            customerRepository.saveAll(
                    List.of(
                            Customer.builder().name("hassane").email("hassane@gmail.com").build(),
                            Customer.builder().name("hassane2").email("hassane2@gmail.com").build(),
                            Customer.builder().name("hassane3").email("hassane3@gmail.com").build(),
                            Customer.builder().name("hassane4").email("hassane4@gmail.com").build()
                    )
            );

            customerRepository.findAll().forEach(System.out::println);
        };
    }
}
