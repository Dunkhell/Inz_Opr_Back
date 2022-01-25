package com.billennium.vaccinationproject.repository;

import com.billennium.vaccinationproject.entity.VaccineEntity;
import com.billennium.vaccinationproject.utilities.VaccineDose;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends JpaRepository<VaccineEntity, Long> {
    Page<VaccineEntity> findAll (Pageable pageable);
    Page<VaccineEntity> findByVaccineDose(VaccineDose vaccineDose, Pageable pageable);
}
