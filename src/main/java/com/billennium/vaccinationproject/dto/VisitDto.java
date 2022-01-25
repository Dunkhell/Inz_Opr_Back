package com.billennium.vaccinationproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
public class VisitDto {

    private Long id;

    @NotNull
    @DateTimeFormat(
            iso = DateTimeFormat.ISO.DATE
    )
    private LocalDate visitDate;

    @NotNull
    @JsonFormat(
            pattern = "HH:mm"
    )
    private LocalTime visitDateTime;

    private UserDetailsDto userDetails;

    private Boolean tookPlace;

    private VaccineDto vaccine;

    private FacilityDto facility;

    public LocalTime getVisitDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String text = visitDateTime.format(formatter);
        return LocalTime.parse(text, formatter);
    }
}
