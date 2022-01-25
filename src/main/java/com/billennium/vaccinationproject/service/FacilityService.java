package com.billennium.vaccinationproject.service;

import com.billennium.vaccinationproject.dto.FacilityDto;
import com.billennium.vaccinationproject.entity.FacilityEntity;
import com.billennium.vaccinationproject.exceptionhandler.ApiRequestException;
import com.billennium.vaccinationproject.repository.FacilityRepository;
import com.billennium.vaccinationproject.utilities.Headers;
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
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final ModelMapper modelMapper;

    public void addFacility (FacilityDto facilityDto) {
        facilityRepository.save(dtoToEntity(facilityDto));
    }

    public ResponseEntity<List<FacilityDto>> getAllFacilities (int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<FacilityEntity> facilityPage = facilityRepository.findAll(pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Headers.NUMBER_OF_PAGES, String.valueOf(facilityPage.getTotalPages()));
        headers.add(Headers.NUMBER_OF_ITEMS, String.valueOf(facilityPage.getTotalElements()));
        headers.add(Headers.CURRENT_PAGE, String.valueOf(page));
        headers.add(Headers.CURRENT_SIZE, String.valueOf(size));


        if(facilityPage.hasContent()) {
            List<FacilityDto> list = entityToDto(facilityPage.getContent());
            return new ResponseEntity<>(list ,headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
        }
    }

    public FacilityDto findFacilityById (Long id) {
        return entityToDto(facilityRepository.findById(id)
                .orElseThrow(
                        () -> new ApiRequestException("Facility not found")));
    }

    public void deleteFacilityById (Long id) {
        FacilityEntity foundFacility = facilityRepository.findById(id).orElseThrow(() -> new ApiRequestException("Facility not found"));
        facilityRepository.delete(foundFacility);
    }

    public void updateFacilityById (Long id, FacilityDto facilityDto) {
        FacilityEntity foundFacility = facilityRepository.findById(id).orElseThrow(() -> new ApiRequestException("Facility not found"));
        foundFacility.setName(
                facilityDto.getName()
        );
        foundFacility.setCity(
                facilityDto.getCity()
        );
        foundFacility.setCountry(
                facilityDto.getCountry()
        );
        foundFacility.setStreet(
                facilityDto.getStreet()
        );
        foundFacility.setContactPhone(
                facilityDto.getContactPhone()
        );
        facilityRepository.save(foundFacility);
    }

    public FacilityDto entityToDto (FacilityEntity facility) {
        return modelMapper.map(facility, FacilityDto.class);
    }

    public FacilityEntity dtoToEntity (FacilityDto facilityDto) {
        return modelMapper.map(facilityDto, FacilityEntity.class);
    }

    public List<FacilityDto> entityToDto (List<FacilityEntity> facilities) {
        return facilities.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public List<FacilityEntity> dtoToEntity (List<FacilityDto> facilityDtos) {
        return facilityDtos.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}
