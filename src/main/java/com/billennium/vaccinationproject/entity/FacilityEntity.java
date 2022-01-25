package com.billennium.vaccinationproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "facility")
public class FacilityEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id"
    )
    private Long id;

    @Column(
            name = "name"
    )
    private String name;

    @Column(
            name = "country"
    )
    private String country;

    @Column(
            name = "city"
    )
    private String city;

    @Column(
            name = "street"
    )
    private String street;

    @Column(
            name = "contact_phone"
    )
    private String contactPhone;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "facility"
    )
    private List<VisitEntity> visits;

    @JsonIgnore
    public List<VisitEntity> getVisits() {
        return visits;
    }

}
