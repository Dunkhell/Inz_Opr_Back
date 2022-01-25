package com.billennium.vaccinationproject.dto;

import com.billennium.vaccinationproject.utilities.Manufacturer;
import com.billennium.vaccinationproject.utilities.VaccineDose;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class VaccineDto {

    private Long id;

    @NotNull
    private Manufacturer manufacturer;

    @NotNull
    @DateTimeFormat(
            iso = DateTimeFormat.ISO.DATE
    )
    private LocalDate expirationDate;

    @NotNull
    @NotBlank
    @Size(
            min = 3
    )
    private String name;

    @NotNull
    private VaccineDose vaccineDose;
}
