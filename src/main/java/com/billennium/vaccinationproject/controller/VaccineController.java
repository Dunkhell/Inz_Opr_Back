package com.billennium.vaccinationproject.controller;

import com.billennium.vaccinationproject.dto.VaccineDto;
import com.billennium.vaccinationproject.service.VaccineService;
import com.billennium.vaccinationproject.utilities.VaccineDose;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("admin/vaccines")
@AllArgsConstructor
public class VaccineController {

    private final VaccineService vaccineService;

    @GetMapping
    public ResponseEntity<List<VaccineDto>> getAllVaccines (@RequestParam(name = "dose", required = false) VaccineDose vaccineDose,
                                                            @RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "10") int size,
                                                            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        return vaccineService.getAllVaccines(vaccineDose ,page, size, sortBy);
    }

    @GetMapping("{id}")
    public ResponseEntity<VaccineDto> findVaccineById (@PathVariable("id") Long id) {
        return new ResponseEntity<>(vaccineService.findVaccineById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VaccineDto> addVaccine (@RequestBody @Valid VaccineDto vaccineDto) {
        vaccineService.addVaccine(vaccineDto);
        return new ResponseEntity<>(vaccineDto, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteVaccineById (@PathVariable("id") Long id) {
        vaccineService.deleteVaccineById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<VaccineDto> updateVaccineById (@PathVariable("id") Long id,
                                                         @RequestBody @Valid VaccineDto vaccineDto) {
        vaccineService.updateVaccineById(id, vaccineDto);
        return new ResponseEntity<>(vaccineDto, HttpStatus.OK);
    }
}
