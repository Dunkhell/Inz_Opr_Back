package com.billennium.vaccinationproject.dto;

import com.billennium.vaccinationproject.utilities.Gender;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UserDetailsToUpdateDto {

    private Long id;

    @NotNull
    @DateTimeFormat(
            iso= DateTimeFormat.ISO.DATE
    )
    private LocalDate dateOfBirth;

    @NotBlank
    @Size(
            min = 2
    )
    private String firstName;

    @Size(
            min = 2
    )
    private String lastName;

    private String otherNames;

    @NotBlank
    private String contactPhone;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotNull
    private Gender gender;
}
