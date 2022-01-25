package com.billennium.vaccinationproject.controller;

import com.billennium.vaccinationproject.dto.VisitDto;
import com.billennium.vaccinationproject.dto.VisitsInBatchDto;
import com.billennium.vaccinationproject.service.VisitService;
import com.billennium.vaccinationproject.utilities.Manufacturer;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
public class VisitController {

    private VisitService visitService;

    @GetMapping("admin/visits")
    public ResponseEntity<List<VisitDto>> getAllVisits (@RequestParam(required = false, name = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                            @Future LocalDate fromDate,
                                                        @RequestParam(required = false, name = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                            @Future LocalDate toDate,
                                                        @RequestParam(name = "city", defaultValue = "%") String city,
                                                        @RequestParam(name = "facilityName", defaultValue = "%") String facilityName,
                                                        @RequestParam(name = "firstName", defaultValue = "%") String firstName,
                                                        @RequestParam(name = "lastName", defaultValue = "%") String lastName,
                                                        @RequestParam(name = "manufacturer", required = false) Manufacturer manufacturer,
                                                        @RequestParam(name = "page", defaultValue = "0") int page,
                                                        @RequestParam(name = "size", defaultValue = "10") int size,
                                                        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        return visitService.getAllVisits(fromDate, toDate, city, manufacturer, facilityName, firstName, lastName, page, size, sortBy);
    }

    @GetMapping("user/visits/vaccination-dates")
    public ResponseEntity<List<VisitDto>> getAllVaccinationDates (@RequestParam(required = false, name = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                        @Future LocalDate fromDate,
                                                                  @RequestParam(required = false, name = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                        @Future LocalDate toDate,
                                                                  @RequestParam(name = "city", defaultValue = "%") String city,
                                                                  @RequestParam(name = "manufacturer", required = false) Manufacturer manufacturer,
                                                                  @RequestParam(name = "facilityName", defaultValue = "%") String facilityName,
                                                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                                                  @RequestParam(name = "size", defaultValue = "10") int size,
                                                                  @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        return visitService.getAllVaccinationDates(fromDate, toDate, city, manufacturer, facilityName, page, size, sortBy);
    }

    @GetMapping("user/visits/vaccination-dates/myvisits")
    public ResponseEntity<List<VisitDto>> getUserVaccinationDates () {
        return new ResponseEntity<>(visitService.getUserVaccinationDates(), HttpStatus.OK);
    }

    @GetMapping("user/visits/vaccination-dates/{id}")
    public ResponseEntity<VisitDto> getVaccinationDatesById (@PathVariable("id") Long id) {
        return new ResponseEntity<>(visitService.getVaccinationDatesById(id), HttpStatus.OK);
    }

    @PutMapping("user/visits/vaccination-dates/{id}")
    public ResponseEntity<?> registerForVisit (@PathVariable("id") Long id) {
        visitService.registerForVisit(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("admin/visits/{id}")
    public ResponseEntity<VisitDto> getVisitById (@PathVariable("id") Long id) {
        return new ResponseEntity<>(visitService.findVisitById(id), HttpStatus.OK);
    }

    @PostMapping("admin/visits/{facility_id}/{vaccine_id}")
    public ResponseEntity<VisitDto> addNewVisit (@PathVariable("facility_id") Long facility_id,
                                                 @PathVariable("vaccine_id") Long vaccine_id,
                                                 @RequestBody @Valid VisitDto visitDto) {
        visitService.addVisit(visitDto, facility_id, vaccine_id);
        return new ResponseEntity<>(visitDto, HttpStatus.CREATED);
    }

    @DeleteMapping("admin/visits/{id}")
    public ResponseEntity<?> deleteVisit (@PathVariable("id") Long id) {
        visitService.deleteVisitById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("admin/visits/{id}")
    public ResponseEntity<VisitDto> updateVisitById (@PathVariable("id") Long id,
                                               @RequestBody @Valid VisitDto visitDto) {
        visitService.updateVisitById(id, visitDto);
        return new ResponseEntity<>(visitDto, HttpStatus.OK);
    }

    @PutMapping("admin/visits/{id}/confirm")
    public ResponseEntity<?> confirmVisit (@PathVariable("id") Long id) {
        visitService.confirmVisitById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("user/visits/vaccination-dates/today")
    public ResponseEntity<List<VisitDto>> getVisitsToday(@RequestParam(name = "page", defaultValue = "0") int page,
                                                         @RequestParam(name = "size", defaultValue = "10") int size,
                                                         @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        return new ResponseEntity<>(visitService.getVisitsToday(page,size,sortBy), HttpStatus.OK);
    }

    @PutMapping("admin/visits/{visitId}/user-register")
    public ResponseEntity<?> userRegister (@PathVariable("visitId") Long visitId,
                                           @RequestParam Long userId){
        visitService.registerUserForVisit(visitId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("admin/visits/batch/{facility_id}/{vaccine_id}")
    public ResponseEntity<?> addVisitsForDayInBatches(@PathVariable("facility_id") Long facility_id,
                                                      @PathVariable("vaccine_id") Long vaccine_id,
                                                      @RequestBody VisitsInBatchDto visitsInBatchDto) {
        visitService.addVisitsForDayInBatches(facility_id, vaccine_id, visitsInBatchDto.getDay(),visitsInBatchDto.getStart(), visitsInBatchDto.getEnd(),visitsInBatchDto.getInterval());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("admin/visits/deleteoldandunused")
    public ResponseEntity<?> deleteOldAndUnusedVisits () {
        visitService.deleteOldAndUnusedVisits();
        return new ResponseEntity<>( HttpStatus.OK );
    }
}
