package com.billennium.vaccinationproject.security;

import com.billennium.vaccinationproject.repository.UserRepository;
import com.billennium.vaccinationproject.security.auth.AppUserService;
import com.billennium.vaccinationproject.security.filter.GoogleTokenAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.billennium.vaccinationproject.security.AppUserRole.*;

@Configuration
@EnableWebSecurity
@Profile("!unsecured")
@Order(2)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final AppUserService appUserService;
    private final UserRepository userRepository;


    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder, AppUserService appUserService, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.appUserService = appUserService;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().cors()
                .and()
                .authorizeRequests()
                    .antMatchers("/index", "/logout","/profile/**")
                        .authenticated()
                    .antMatchers(HttpMethod.PUT,"/admin/users/**")
                        .hasRole(ADMIN.name())
                    .antMatchers("/admin/**")
                        .hasAnyRole(ADMIN.name(), MEDICAL_STAFF.name())
                    .antMatchers("/user/**")
                        .hasAnyRole(USER.name(), ADMIN.name(), MEDICAL_STAFF.name())
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new GoogleTokenAuthentication(userRepository, authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(appUserService);
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/error",
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/webjars/**");
    }
}
