package com.billennium.vaccinationproject.security.auth;

import com.billennium.vaccinationproject.entity.UserEntity;

import java.util.Optional;

public interface AppUserDAO {
    Optional<UserEntity> selectAppUserByUsername(String username);
}
