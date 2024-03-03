package org.avegarlabs.userservice;

import lombok.extern.slf4j.Slf4j;
import org.avegarlabs.userservice.models.User;
import org.avegarlabs.userservice.models.enums.UserRoles;
import org.avegarlabs.userservice.repositories.UserRepository;
import org.avegarlabs.userservice.util.CreateMoniker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(UserRepository usersRepository, PasswordEncoder encoder, CreateMoniker moniker) {
        return args -> {
            List<User> userList = usersRepository.findAll();
            if (userList.size() == 0) {
                List<UserRoles> roles = new ArrayList<>();
                roles.add(UserRoles.ROLE_ADMIN);
                roles.add(UserRoles.ROLE_USER);
                log.info("Init Admin User");
                User admin = new User();
                admin.setName("Admin User");
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin"));
                admin.setEmail("admin@email.com");
                admin.setRoleList(roles);
                admin.setMoniker(moniker.createMoniker("Admin User"));
                usersRepository.save(admin);
            }
        };

    }

}
