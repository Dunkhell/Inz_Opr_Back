package com.billennium.vaccinationproject.security.filter;

import com.billennium.vaccinationproject.exceptionhandler.ApiRequestException;
import com.billennium.vaccinationproject.utilities.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GoogleTokenFilter extends OncePerRequestFilter {

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletResponse response) {
        String token = response.getHeader(SecurityConstants.TOKEN_HEADER);
        if (token != null && !token.isEmpty() && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            try {
                byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

                Jws<Claims> parsedToken = Jwts.parser()
                        .setSigningKey(signingKey)
                        .parseClaimsJws(token.replace("Bearer ", ""));

                String username = parsedToken
                        .getBody()
                        .getSubject();

                List<GrantedAuthority> authorities = (List)parsedToken.getBody()
                        .get("rol", List.class).stream()
                        .map(authority -> new SimpleGrantedAuthority("ROLE_" + authority))
                        .collect(Collectors.toList());

                if (username != null && !username.isEmpty()) {
                    return new UsernamePasswordAuthenticationToken(username, null, authorities);
                }
            } catch (Exception exception) {
                // this shouldn't happen... if it does it's handled inside doFilterInternal while this method returns null;
            }
        }

        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = getAuthentication(response);
        if (authentication == null) {
            throw new ApiRequestException("Unauthorized user breach!", HttpStatus.UNAUTHORIZED);
        } else {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
    }
}
