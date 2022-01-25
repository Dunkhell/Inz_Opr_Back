package com.billennium.vaccinationproject.service;

import com.billennium.vaccinationproject.dto.VisitDto;
import com.billennium.vaccinationproject.entity.*;
import com.billennium.vaccinationproject.exceptionhandler.ApiRequestException;
import com.billennium.vaccinationproject.repository.FacilityRepository;
import com.billennium.vaccinationproject.repository.UserRepository;
import com.billennium.vaccinationproject.repository.VaccineRepository;
import com.billennium.vaccinationproject.repository.VisitRepository;
import com.billennium.vaccinationproject.security.auth.AppUserService;
import com.billennium.vaccinationproject.utilities.Headers;
import com.billennium.vaccinationproject.utilities.Manufacturer;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private VaccineRepository vaccineRepository;
    private FacilityRepository facilityRepository;
    private UserRepository userRepository;
    private AppUserService appUserService;
    private ModelMapper modelMapper;

    public void addVisit (VisitDto visitDto, Long facility_id, Long vaccine_id) {

        VisitEntity newVisit = dtoToEntity(visitDto);

        newVisit.setTookPlace( false );

        newVisit.setFacility(
                facilityRepository.findById(facility_id).orElseThrow(()
                        -> new ApiRequestException("Facility not found"))
        );

        newVisit.setVaccine(
                vaccineRepository.findById(vaccine_id).orElseThrow(()
                        -> new ApiRequestException("Vaccine not found"))
        );
        if (visitDateAndTimeEntity(newVisit).isEmpty()){
            visitRepository.save(newVisit);
        } else {
            throw new ApiRequestException("Visit date already taken!", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<List<VisitDto>>  getAllVisits (LocalDate fromDate,
                                                         LocalDate toDate,
                                                         String city,
                                                         Manufacturer manufacturer,
                                                         String facilityName,
                                                         String firstName,
                                                         String lastName,
                                                         int page,
                                                         int size,
                                                         String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<VisitEntity> visitPage;
        //Default values for LocalDates
        if(fromDate==null){
            fromDate=LocalDate.now();
        }
        if(toDate==null){
            toDate=(LocalDate.now().plusYears(1));
        }
        if(!city.equals("%")){
            city="%"+city+"%";
        }
        if(!facilityName.equals("%")){
            facilityName="%"+facilityName+"%";
        }
        if(!firstName.equals("%")){
            firstName="%"+firstName+"%";
        }
        if(!lastName.equals("%")){
            lastName="%"+lastName+"%";
        }
        if(manufacturer == null){
            if(firstName.equals("%") || lastName.equals("%")){
                visitPage = visitRepository.findByVisitDateBetweenAndFacilityCityLikeAndFacilityNameLike(fromDate,
                        toDate,
                        city,
                        facilityName,
                        pageable);
            }
            else {
                visitPage = visitRepository.findByVisitDateBetweenAndFacilityCityLikeAndFacilityNameLikeAndUserDetailsFirstNameLikeAndUserDetailsLastNameLike(  fromDate,
                        toDate,
                        city,
                        facilityName,
                        firstName,
                        lastName,
                        pageable);
            }
        }
        else{
            if(firstName.equals("%") || lastName.equals("%")){
                visitPage = visitRepository.findByVisitDateBetweenAndFacilityCityLikeAndVaccineManufacturerAndFacilityNameLike(fromDate,
                        toDate,
                        city,
                        manufacturer,
                        facilityName,
                        pageable);
            }
            else{
                visitPage = visitRepository.findByVisitDateBetweenAndFacilityCityLikeAndVaccineManufacturerAndFacilityNameLikeAndUserDetailsFirstNameLikeAndUserDetailsLastNameLike(fromDate,
                        toDate,
                        city,
                        manufacturer,
                        facilityName,
                        firstName,
                        lastName,
                        pageable);

            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(Headers.NUMBER_OF_PAGES, String.valueOf(visitPage.getTotalPages()));
        headers.add(Headers.NUMBER_OF_ITEMS, String.valueOf(visitPage.getTotalElements()));
        headers.add(Headers.CURRENT_PAGE, String.valueOf(page));
        headers.add(Headers.CURRENT_SIZE, String.valueOf(size));

        if(visitPage.hasContent()) {
            List<VisitDto> list = entityToDto(visitPage.getContent());
            return new ResponseEntity<>(list,headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<VisitDto>> getAllVaccinationDates (LocalDate fromDate,
                                                                  LocalDate toDate,
                                                                  String city,
                                                                  Manufacturer manufacturer,
                                                                  String facilityName,
                                                                  int page,
                                                                  int size,
                                                                  String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<VisitEntity> visitPage;
        //Default values for LocalDates
        if(fromDate==null || fromDate.isAfter(LocalDate.now())){
            fromDate=LocalDate.now();
        }
        if(toDate==null){
            toDate=(LocalDate.now().plusYears(1));
        }
        if(!city.equals("%")){
            city="%"+city+"%";
        }
        if(!facilityName.equals("%")){
            facilityName="%"+facilityName+"%";
        }
        if(manufacturer == null){
             visitPage = visitRepository.findByVisitDateBetweenAndFacilityCityLikeAndUserDetailsIsNullAndFacilityNameLike(fromDate,
                                                                                                                         toDate,
                                                                                                                         city,
                                                                                                                         facilityName,
                                                                                                                         pageable);
        }
        else{
            visitPage = visitRepository.findByVisitDateBetweenAndFacilityCityLikeAndVaccineManufacturerAndUserDetailsIsNullAndFacilityNameLike( fromDate,
                                                                                                                                                toDate,
                                                                                                                                                city,
                                                                                                                                                manufacturer,
                                                                                                                                                facilityName,
                                                                                                                                                pageable);
        }



        if(visitPage.hasContent()) {
            List<VisitDto> list = entityToDto(
                    new ArrayList<>(visitPage.getContent())
            );

            HttpHeaders headers = new HttpHeaders();
            headers.add(Headers.NUMBER_OF_PAGES, String.valueOf(visitPage.getTotalPages()));
            headers.add(Headers.NUMBER_OF_ITEMS, String.valueOf(visitPage.getTotalElements()));
            headers.add(Headers.CURRENT_PAGE, String.valueOf(page));
            headers.add(Headers.CURRENT_SIZE, String.valueOf(size));

            return new ResponseEntity<>(list,headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
        }
    }

    public VisitDto getVaccinationDatesById (Long id) {
        VisitEntity visit = visitRepository.findById(id).orElseThrow(
                () -> new ApiRequestException("No visit found with id " + id)
        );

        if (visit.getUserDetails() != null) {
            throw new ApiRequestException("Visit occupied.", HttpStatus.UNAUTHORIZED);
        }

        boolean dateNotFromFuture = visit.getVisitDate().isAfter(LocalDate.now().minusDays(1));

        if( !dateNotFromFuture ) {
            throw new ApiRequestException("Visit date is already in the past", HttpStatus.BAD_REQUEST);
        }

        return entityToDto(visit);
    }

    public void deleteOldAndUnusedVisits () {
        visitRepository.findAll().stream().filter(
                visit -> visit.getUserDetails() == null
                        && visit.getVisitDate().compareTo( LocalDate.now() ) < 0
                        || ( visit.getVisitDate().compareTo( LocalDate.now() ) == 0
                            && visit.getVisitDateTime().compareTo( LocalTime.now() ) < 0 )
        ).forEach( visitRepository::delete );
    }

    public void registerForVisit (Long visit_id) {
        VisitEntity foundVisit = visitRepository.findById(visit_id).orElseThrow(() -> new ApiRequestException("Visit not found"));

        if(foundVisit.getUserDetails() == null) {
            UserDetailsEntity userDetails = appUserService.getUserFromCurrentSession().getUserDetails();
            if(!userDetails.getIsVaccinated() && userDetails.getVisits()
                    .stream().noneMatch(visitEntity -> !visitEntity.getTookPlace())) {
                foundVisit.setUserDetails(userDetails);
                visitRepository.save(foundVisit);
            } else {
                throw new ApiRequestException("User is already vaccinated or signed on some other visit", HttpStatus.BAD_REQUEST);
            }
        }
    }

    public void registerUserForVisit(Long visitId, Long userId) {
        VisitEntity foundVisit = visitRepository.findById(visitId)
                .orElseThrow(() -> new ApiRequestException("Visit not found"));

        if(foundVisit.getUserDetails() == null) {
            UserDetailsEntity userDetails = userRepository.findById(userId)
                    .orElseThrow(() -> new ApiRequestException("User not found"))
                    .getUserDetails();
            if(!userDetails.getIsVaccinated() && userDetails.getVisits()
                    .stream().noneMatch(visitEntity -> !visitEntity.getTookPlace())) {
                foundVisit.setUserDetails(userDetails);
                visitRepository.save(foundVisit);
            } else {
                throw new ApiRequestException("User is already vaccinated", HttpStatus.BAD_REQUEST);
            }
        }
        else{
            throw new ApiRequestException("Visit is already taken", HttpStatus.BAD_REQUEST);
        }
    }

    public VisitDto findVisitById (Long id) {
        return entityToDto(visitRepository.findById(id).orElseThrow(
                () -> new ApiRequestException("Visit not found")));
    }

    public void deleteVisitById (Long id) {
        VisitEntity foundVisit = visitRepository.findById(id).orElseThrow(() -> new ApiRequestException("Visit not found"));
        visitRepository.delete(foundVisit);
    }


    public void updateVisitById (Long id, VisitDto visitDto) {
        VisitEntity foundVisit = visitRepository.findById(id).orElseThrow(() -> new ApiRequestException("Visit not found"));
        foundVisit.setVisitDate(
                visitDto.getVisitDate()
        );
        foundVisit.setVisitDateTime(
                visitDto.getVisitDateTime()
        );
        FacilityEntity foundFacility = facilityRepository.findById( visitDto.getFacility().getId() )
                .orElseThrow(() -> new ApiRequestException( "Facility Not Found",HttpStatus.NOT_FOUND ));
        foundVisit.setFacility( foundFacility );

        VaccineEntity foundVaccine = vaccineRepository.findById( visitDto.getVaccine().getId() )
                .orElseThrow(() -> new ApiRequestException( "Vaccine Not Found",HttpStatus.NOT_FOUND ));
        foundVisit.setVaccine( foundVaccine );

        Optional<VisitEntity> visitConflict = visitDateAndTimeEntity(foundVisit);

        if (visitConflict.isEmpty() || visitConflict.get().getId().equals(foundVisit.getId())) {
            visitRepository.save(foundVisit);
        } else {
            throw new ApiRequestException("Visit date already taken!", HttpStatus.CONFLICT);
        }
    }

    public void confirmVisitById (Long id) {
        VisitEntity foundVisit = visitRepository.findById(id).orElseThrow(() -> new ApiRequestException("Visit not found"));
        if(foundVisit.getUserDetails() != null) {
            foundVisit.setTookPlace(true);
            Integer doseValue = foundVisit.getVaccine().getVaccineDose().getDoseValue();
            long visitThatTookPlaceCount = foundVisit.getUserDetails().getVisits().stream()
                    .filter(VisitEntity::getTookPlace).count();
            if (doseValue == visitThatTookPlaceCount) {
                foundVisit.getUserDetails().setIsVaccinated(true);
            }
            visitRepository.save(foundVisit);
        } else {
            throw new ApiRequestException("Visit is not assigned to an user");
        }
    }

    public List<VisitDto> getVisitsToday(int page, int size, String sortBy) {
        Pageable request = PageRequest.of(page,size, Sort.by(sortBy));

        Page<VisitEntity> visitsToday = visitRepository.findAllByVisitDate(LocalDate.now(),request);

        if (visitsToday.hasContent()) {
            return entityToDto(visitsToday.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    public void addVisitsForDayInBatches(Long facility_id, Long vaccine_id, LocalDate day, LocalTime start, LocalTime end, Long interval) {
        LocalDate yesterday = LocalDate.now().minus(1, ChronoUnit.DAYS);
        boolean validDateNotFromPast = day.isAfter(yesterday);
        if (!validDateNotFromPast) {
            throw new ApiRequestException("Given date is from the past!", HttpStatus.NOT_ACCEPTABLE);
        } else {
            VaccineEntity vaccine = vaccineRepository.findById(vaccine_id).orElseThrow(
                    () -> new ApiRequestException("Vaccine not found", HttpStatus.NOT_FOUND)
            );
            FacilityEntity facility = facilityRepository.findById(facility_id).orElseThrow(
                    () -> new ApiRequestException("Facility not found", HttpStatus.NOT_FOUND)
            );

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String text = start.format(formatter);
            LocalTime startTime = LocalTime.parse(text, formatter);

            while (startTime.isBefore(end) || startTime.equals(end)){
                VisitEntity visit = new VisitEntity();
                visit.setVisitDate(day);
                visit.setFacility(facility);
                visit.setVaccine(vaccine);
                visit.setVisitDateTime(startTime);
                visit.setTookPlace(false);

                if(visitDateAndTimeEntity(visit).isEmpty()) {
                    visitRepository.save(visit);
                }
                startTime = startTime.plusMinutes(interval);
            }
        }
    }

    private Optional<VisitEntity> visitDateAndTimeEntity(VisitEntity visit) {
        return visitRepository.findByVisitDateAndVisitDateTime(visit.getVisitDate(),visit.getVisitDateTime());
    }

    public List<VisitDto> getUserVaccinationDates() {
        UserEntity user = appUserService.getUserFromCurrentSession();
        return entityToDto( user.getUserDetails().getVisits() );
    }

    public VisitDto entityToDto (VisitEntity visit) {
        return modelMapper.map(visit, VisitDto.class);
    }

    public VisitEntity dtoToEntity (VisitDto visitDto) {
        return modelMapper.map(visitDto, VisitEntity.class);
    }

    public List<VisitDto> entityToDto (List<VisitEntity> visits) {
        return visits.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public List<VisitEntity> dtoToEntity (List<VisitDto> visitDtos) {
        return visitDtos.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }

}
