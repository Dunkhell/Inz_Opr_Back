package com.billennium.vaccinationproject.security.filter;

import com.billennium.vaccinationproject.entity.UserDetailsEntity;
import com.billennium.vaccinationproject.entity.UserEntity;
import com.billennium.vaccinationproject.repository.UserRepository;
import com.billennium.vaccinationproject.security.AppUserRole;
import com.billennium.vaccinationproject.utilities.SecurityConstants;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Synchronized;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GoogleTokenAuthentication extends AbstractAuthenticationProcessingFilter {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private static final HttpTransport transport = new NetHttpTransport();
    private static final JsonFactory jsonFactory = new GsonFactory();
    private final String clientId;

    public GoogleTokenAuthentication(UserRepository userRepository, AuthenticationManager authenticationManager) {
        super("/**");
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        clientId = "615859748148-uncsb6e0j23okfql5nagjtt65houtev0.apps.googleusercontent.com";
    }

    @Synchronized
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport,jsonFactory)
                .setAudience(Collections.singletonList(clientId)).build();

        String tokenString;

        try {
            tokenString = request.getHeader( "GoogleAuthorization" ).replace( "Bearer ", "" );
        } catch (NullPointerException e){
            throw new BadCredentialsException( "Missing authentication header!");
        }

        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(tokenString);
        } catch (GeneralSecurityException | IOException e) {
            throw new BadCredentialsException("Something's wrong with idToken");
        }

        if ( idToken != null ) {
            Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");
            String usernameTemplate = email.split("@")[0].toLowerCase(); //Getting first part of email and setting it as stand-in username

            Optional<UserEntity> authUser = userRepository.getByEmail(email);

            if (authUser.isEmpty()) {
                UserEntity user = new UserEntity();
                UserDetailsEntity userDetails = new UserDetailsEntity();

                user.setUsername(usernameTemplate);
                user.setApplicationUserRole(AppUserRole.USER); //Setting new user role to USER as default.

                user.setEmail(email);
                userDetails.setFirstName(givenName);
                userDetails.setLastName(familyName);
                userDetails.setIsVaccinated(false);

                user.setUserDetails(userDetails);
                userRepository.save(user);
            }

            UserEntity loggedInUser= userRepository.getByUsername(usernameTemplate).orElseThrow(
                    () -> new BadCredentialsException("Error loading username"));

            Authentication authentication = new UsernamePasswordAuthenticationToken(loggedInUser.getUsername(), null ,loggedInUser.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return SecurityContextHolder.getContext().getAuthentication();
        }
        throw new BadCredentialsException("Invalid Auth Token!");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        UserEntity user = userRepository.getByUsername((String)authentication.getPrincipal()).orElseThrow(
                ()->new BadCredentialsException("Unauthorized access attempt!")
        );

        List<String> roles =  user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .claim("rol", roles)
                .compact();

        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
        chain.doFilter(request,response);
    }

}