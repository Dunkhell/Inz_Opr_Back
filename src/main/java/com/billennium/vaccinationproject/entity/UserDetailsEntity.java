package com.billennium.vaccinationproject.entity;

import com.billennium.vaccinationproject.utilities.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_details")
public class UserDetailsEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id"
    )
    private Long id;

    @Column(
            name = "date_of_birth"
    )
    private LocalDate dateOfBirth;

    @Column(
            name = "first_name"
    )
    private String firstName;

    @Column(
            name = "last_name"
    )
    private String lastName;

    @Column(
            name = "other_names"
    )
    private String otherNames;

    @Column(
            name = "contact_phone"
    )
    private String contactPhone;

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
            name = "gender"
    )
    @Enumerated(
            value = EnumType.ORDINAL
    )
    private Gender gender;

    @OneToOne(
            mappedBy = "userDetails"
    )
    private UserEntity user;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "userDetails"
    )
    private List<VisitEntity> visits;

    @Column(
            name  = "is_vaccinated"
    )
    private Boolean isVaccinated;

    @JsonIgnore
    public UserEntity getUser() {
        return user;
    }
}