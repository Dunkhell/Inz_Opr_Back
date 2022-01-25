package com.billennium.vaccinationproject.dto;

import com.billennium.vaccinationproject.security.AppUserRole;
import lombok.*;

import javax.validation.constraints.*;

@Data
public class UserDto {
        @NotNull
        private Long id;

        @NotBlank
        @Size(
                min = 4
        )
        private String username;

        @NotBlank
        @Size(
                min = 4
        )
        private String password;

        @Email
        private String email;

        @NotNull
        private AppUserRole applicationUserRole;

        @NotNull
        private UserDetailsDto userDetailsDTO;
}
