package com.billennium.vaccinationproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "visit")
public class VisitEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id"
    )
    private Long id;

    @Column(
            name = "visit_date"
    )
    private LocalDate visitDate;

    @Column(
            name = "visit_date_time"
    )
    private LocalTime visitDateTime;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(
            name = "patient_id"
    )
    private UserDetailsEntity userDetails;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(
            name = "facility_id",
            nullable = false
    )
    private FacilityEntity facility;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(
            name = "vaccine_id"
    )
    private VaccineEntity vaccine;

    private Boolean tookPlace;

    @JsonIgnore
    public FacilityEntity getFacility() {
        return facility;
    }

    @JsonIgnore
    public VaccineEntity getVaccine() {
        return vaccine;
    }

}
