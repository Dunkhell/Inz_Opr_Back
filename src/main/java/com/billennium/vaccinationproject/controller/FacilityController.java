package com.billennium.vaccinationproject.controller;

import com.billennium.vaccinationproject.dto.FacilityDto;
import com.billennium.vaccinationproject.service.FacilityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("admin/facilities")
@AllArgsConstructor
public class FacilityController {

    private final FacilityService facilityService;

    @GetMapping
    public ResponseEntity<List<FacilityDto>> getAllFacilities (@RequestParam(name = "page", defaultValue = "0") int page,
                                                              @RequestParam(name = "size", defaultValue = "10") int size,
                                                              @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        return facilityService.getAllFacilities(page, size, sortBy);
    }

    @GetMapping("{id}")
    public ResponseEntity<FacilityDto> getFacilityById (@PathVariable("id") Long id) {
        return new ResponseEntity<>(facilityService.findFacilityById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FacilityDto> addNewFacility (@RequestBody @Valid FacilityDto facilityDto) {
        facilityService.addFacility(facilityDto);
        return new ResponseEntity<>(facilityDto, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteFacility (@PathVariable("id") Long id) {
        facilityService.deleteFacilityById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<FacilityDto> updateFacility (@PathVariable("id") Long id,
                                @RequestBody @Valid FacilityDto facilityDto) {
        facilityService.updateFacilityById(id, facilityDto);
        return new ResponseEntity<>(facilityDto, HttpStatus.OK);
    }
}
