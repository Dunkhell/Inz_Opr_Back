package com.billennium.vaccinationproject.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class VisitsInBatchDto {

    LocalDate day;

    LocalTime start;

    LocalTime end;

    Long interval;

}
