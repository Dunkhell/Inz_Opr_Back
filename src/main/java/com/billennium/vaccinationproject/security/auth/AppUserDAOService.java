package com.billennium.vaccinationproject.security.auth;

import com.billennium.vaccinationproject.entity.UserEntity;
import com.billennium.vaccinationproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("sqlDB")
public class AppUserDAOService implements AppUserDAO{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public AppUserDAOService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserEntity> selectAppUserByUsername(String username) {
        return userRepository.getByUsername(username);
    }
}