package com.example.springsecurity.Config;

import com.example.springsecurity.entity.Role;
import com.example.springsecurity.entity.User;
import com.example.springsecurity.enums.RoleUser;
import com.example.springsecurity.repo.RoleRepository;
import com.example.springsecurity.repo.UserRepository;
import lombok.Builder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Builder
public class DataSeeder {

    @Bean
    CommandLineRunner seedData( UserRepository userRepository,
                                PasswordEncoder passwordEncoder,
                                RoleRepository roleRepository)
    {
        return args -> {
            Role roleUser = roleRepository.findByName(RoleUser.ROLE_USER);
            Role roleAdmin = roleRepository.findByName(RoleUser.ROLE_ADMIN);

            if (!userRepository.existsByUsername("admin")){

                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("123456789"));

                admin.getRoles().add(roleAdmin);
                admin.getRoles().add(roleUser);

                userRepository.save(admin);
            }
        };
    }
}









