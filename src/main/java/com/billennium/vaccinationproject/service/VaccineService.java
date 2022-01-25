package com.billennium.vaccinationproject.service;

import com.billennium.vaccinationproject.dto.VaccineDto;
import com.billennium.vaccinationproject.entity.VaccineEntity;
import com.billennium.vaccinationproject.entity.VisitEntity;
import com.billennium.vaccinationproject.exceptionhandler.ApiRequestException;
import com.billennium.vaccinationproject.repository.VaccineRepository;
import com.billennium.vaccinationproject.repository.VisitRepository;
import com.billennium.vaccinationproject.utilities.Headers;
import com.billennium.vaccinationproject.utilities.VaccineDose;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VaccineService {

    private final VaccineRepository vaccineRepository;
    private final VisitRepository visitRepository;
    private final ModelMapper modelMapper;

    public void addVaccine (VaccineDto vaccineDto) {
        vaccineRepository.save(dtoToEntity(vaccineDto));
    }

    public ResponseEntity<List<VaccineDto>> getAllVaccines (VaccineDose vaccineDose, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<VaccineEntity> vaccinePage;
        if(vaccineDose==null){
            vaccinePage = vaccineRepository.findAll(pageable);
        }
        else {
            vaccinePage = vaccineRepository.findByVaccineDose(vaccineDose, pageable);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(Headers.NUMBER_OF_PAGES, String.valueOf(vaccinePage.getTotalPages()));
        headers.add(Headers.NUMBER_OF_ITEMS, String.valueOf(vaccinePage.getTotalElements()));
        headers.add(Headers.CURRENT_PAGE, String.valueOf(page));
        headers.add(Headers.CURRENT_SIZE, String.valueOf(size));


        if(vaccinePage.hasContent()) {
            List<VaccineDto> list = entityToDto(vaccinePage.getContent());
            return new ResponseEntity<>(list, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
        }
    }

    public VaccineDto findVaccineById (Long id) {
        return entityToDto(vaccineRepository.findById(id).orElseThrow(
                () -> new ApiRequestException("Vaccine not found")));
    }

    public void deleteVaccineById (Long id) {
        VaccineEntity foundVaccine = vaccineRepository.findById(id).orElseThrow(() -> new ApiRequestException("Vaccine not found"));

        for(VisitEntity visit: foundVaccine.getVisits()) {
            visit.setVaccine(null);
            visitRepository.save(visit);
        }

        vaccineRepository.delete(foundVaccine);
    }

    public void updateVaccineById (Long id, VaccineDto vaccineDto) {
        VaccineEntity foundVaccine = vaccineRepository.findById(id).orElseThrow(() -> new ApiRequestException("Vaccine not found"));
        foundVaccine.setName(
                vaccineDto.getName()
        );
        foundVaccine.setExpirationDate(
                vaccineDto.getExpirationDate()
        );
        foundVaccine.setManufacturer(
                vaccineDto.getManufacturer()
        );
        foundVaccine.setVaccineDose(
                vaccineDto.getVaccineDose()
        );
        vaccineRepository.save(foundVaccine);
    }

    public VaccineDto entityToDto (VaccineEntity vaccine) {
        return modelMapper.map(vaccine, VaccineDto.class);
    }

    public VaccineEntity dtoToEntity (VaccineDto vaccineDto) {
        return modelMapper.map(vaccineDto, VaccineEntity.class);
    }

    public List<VaccineDto> entityToDto (List<VaccineEntity> vaccines) {
        return vaccines.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public List<VaccineEntity> dtoToEntity (List<VaccineDto> vaccineDtos) {
        return vaccineDtos.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}
