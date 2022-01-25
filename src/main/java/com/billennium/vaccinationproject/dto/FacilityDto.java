package com.billennium.vaccinationproject.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class FacilityDto {

    private Long id;

    @NotNull
    @NotBlank
    @Size(
            min = 3
    )
    private String name;

    @NotNull
    @NotBlank
    @Size(
            min = 3
    )
    private String country;

    @NotNull
    @NotBlank
    @Size(
            min = 3
    )
    private String city;

    @NotNull
    @NotBlank
    @Size(
            min = 3
    )
    private String street;

    @NotNull
    @NotBlank
    @Size(
            min = 9,
            max = 12
    )
    private String contactPhone;
}
