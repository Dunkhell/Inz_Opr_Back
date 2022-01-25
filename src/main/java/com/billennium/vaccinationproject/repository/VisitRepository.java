package com.billennium.vaccinationproject.repository;

import com.billennium.vaccinationproject.entity.VisitEntity;
import com.billennium.vaccinationproject.utilities.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<VisitEntity, Long> {

    Optional<VisitEntity> findByVisitDateAndVisitDateTime(LocalDate visitDate,
                                                          LocalTime visitDateTime);

    Page<VisitEntity> findAllByVisitDate(LocalDate today,
                                         Pageable pageable);

    Page<VisitEntity> findByVisitDateBetweenAndFacilityCityLikeAndUserDetailsIsNullAndFacilityNameLike( LocalDate fromDate,
                                                                                                        LocalDate toDate,
                                                                                                        String city,
                                                                                                        String facilityName,
                                                                                                        Pageable pageable);


    Page<VisitEntity> findByVisitDateBetweenAndFacilityCityLikeAndVaccineManufacturerAndUserDetailsIsNullAndFacilityNameLike( LocalDate fromDate,
                                                                                                                              LocalDate toDate,
                                                                                                                              String city,
                                                                                                                              Manufacturer manufacturer,
                                                                                                                              String facilityName,
                                                                                                                              Pageable pageable);

    Page<VisitEntity> findByVisitDateBetweenAndFacilityCityLikeAndFacilityNameLike(LocalDate fromDate,
                                                                                    LocalDate toDate,
                                                                                    String city,
                                                                                    String facilityName,
                                                                                    Pageable pageable);
    Page<VisitEntity> findByVisitDateBetweenAndFacilityCityLikeAndFacilityNameLikeAndUserDetailsFirstNameLikeAndUserDetailsLastNameLike(LocalDate fromDate,
                                                                                                                                        LocalDate toDate,
                                                                                                                                        String city,
                                                                                                                                        String facilityName,
                                                                                                                                        String firstName,
                                                                                                                                        String lastName,
                                                                                                                                        Pageable pageable);

    Page<VisitEntity> findByVisitDateBetweenAndFacilityCityLikeAndVaccineManufacturerAndFacilityNameLikeAndUserDetailsFirstNameLikeAndUserDetailsLastNameLike(LocalDate fromDate,
                                                                                      LocalDate toDate,
                                                                                      String city,
                                                                                      Manufacturer manufacturer,
                                                                                      String facilityName,
                                                                                      String firstName,
                                                                                      String lastName,
                                                                                      Pageable pageable);

    Page<VisitEntity> findByVisitDateBetweenAndFacilityCityLikeAndVaccineManufacturerAndFacilityNameLike(LocalDate fromDate,
                                                                                                          LocalDate toDate,
                                                                                                          String city,
                                                                                                          Manufacturer manufacturer,
                                                                                                          String facilityName,
                                                                                                          Pageable pageable);
}
