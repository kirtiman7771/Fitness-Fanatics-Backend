package com.gym.gym_ff_backend.config;

import com.gym.gym_ff_backend.entity.Role;
import com.gym.gym_ff_backend.entity.User;
import com.gym.gym_ff_backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminDataInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        String adminEmail = "admin@fitnessfanatics.com";

        if (userRepository.existsByEmail(adminEmail)) {
            return;
        }

        User admin = new User();

        admin.setEmail(adminEmail);
        admin.setPassword(
                passwordEncoder.encode("Admin@12345"));
        admin.setRole(Role.ADMIN);
        admin.setEnabled(true);
        admin.setMember(null);

        userRepository.save(admin);

        System.out.println("Default admin user created.");
    }
}