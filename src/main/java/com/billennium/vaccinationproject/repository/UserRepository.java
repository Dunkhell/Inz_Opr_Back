package com.billennium.vaccinationproject.repository;

import com.billennium.vaccinationproject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> getByUsername(String username);

    @Query("SELECT u.password FROM UserEntity u WHERE u.username = ?1")
    String selectUserPassword(String username);

    Optional<UserEntity> getByEmail(String email);
}
