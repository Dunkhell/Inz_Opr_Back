package com.billennium.vaccinationproject.entity;

import com.billennium.vaccinationproject.utilities.Manufacturer;
import com.billennium.vaccinationproject.utilities.VaccineDose;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vaccine")
public class VaccineEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id"
    )
    private Long id;

    @Column(
            name = "manufacturer"
    )
    private Manufacturer manufacturer;

    @Column(
            name = "expiration_date"
    )
    private LocalDate expirationDate;

    @Column(
            name = "name"
    )
    private String name;
    @Fetch(FetchMode.JOIN)
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH,
            mappedBy = "vaccine"
    )
    private List<VisitEntity> visits;

    @Column(
            name = "vaccine_dose"
    )
    @Enumerated(
            value = EnumType.ORDINAL
    )
    private VaccineDose vaccineDose;

    @JsonIgnore
    public List<VisitEntity> getVisits() {
        return visits;
    }

}
