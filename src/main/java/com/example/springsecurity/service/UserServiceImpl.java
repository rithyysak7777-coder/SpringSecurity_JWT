package com.example.springsecurity.service;

import com.example.springsecurity.dto.LoginResponse;
import com.example.springsecurity.dto.MessageResponse;
import com.example.springsecurity.dto.RegisterRequest;
import com.example.springsecurity.entity.Role;
import com.example.springsecurity.entity.User;
import com.example.springsecurity.enums.RoleUser;
import com.example.springsecurity.exception.BadRequestException;
import com.example.springsecurity.repo.RoleRepository;
import com.example.springsecurity.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }

    @Override
    public MessageResponse registerUser(RegisterRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new BadRequestException("User already exists"));

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email address already in use");
        }

        User userEntity = new User();

        Role role = roleRepository.findByName(RoleUser.ROLE_USER);

        userEntity.setUsername(request.getUsername());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));

        userEntity.getRoles().add(role);

        userRepository.save(userEntity);

        return new MessageResponse("User registered successfully");
    }

    @Override
    public LoginResponse login(RegisterRequest request) {
        return null;
    }
// take user out and put it in role
}
