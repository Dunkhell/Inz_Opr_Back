package com.billennium.vaccinationproject.security.auth;

import com.billennium.vaccinationproject.entity.UserEntity;
import com.billennium.vaccinationproject.exceptionhandler.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserDAO appUserDAO;

    @Autowired
    public AppUserService(@Qualifier("sqlDB") AppUserDAO appUserDAO) {
        this.appUserDAO = appUserDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserDAO.selectAppUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found."));
    }

    public UserEntity getUserFromCurrentSession () {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = appUserDAO.selectAppUserByUsername(auth.getName()).orElseThrow(
                () -> new ApiRequestException("User was not found"));
        return user;
    }
}
