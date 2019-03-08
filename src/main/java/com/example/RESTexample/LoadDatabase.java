package com.example.RESTexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//declare that this class provides one or more @Bean methods
// and may be processed by the Spring container to generate
// bean definitions and service requests for those beans at runtime.
// autocreate an Slf4j-based LoggerFactory as log,
// allowing us to log these newly created "employees"
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository){
        return args->{
            log.info("Preloading "+ employeeRepository.save(new Employee("Alasdair","Haigh","Nerd Star")));
//            log.info("Proloading "+employeeRepository.save(new Employee("Mica","Nerd wannabe")));
            orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
            orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));

            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
        });
    };
}}
