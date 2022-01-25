package com.billennium.vaccinationproject.repository;

import com.billennium.vaccinationproject.entity.FacilityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<FacilityEntity, Long> {

    Page<FacilityEntity> findAll (Pageable pageable);
}
