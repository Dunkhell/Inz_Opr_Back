package com.billennium.vaccinationproject.service;

import com.billennium.vaccinationproject.dto.UserDetailsDto;
import com.billennium.vaccinationproject.dto.UserDetailsToUpdateDto;
import com.billennium.vaccinationproject.dto.UserDto;
import com.billennium.vaccinationproject.entity.UserDetailsEntity;
import com.billennium.vaccinationproject.entity.UserEntity;
import com.billennium.vaccinationproject.entity.VisitEntity;
import com.billennium.vaccinationproject.exceptionhandler.ApiRequestException;
import com.billennium.vaccinationproject.repository.UserRepository;
import com.billennium.vaccinationproject.security.auth.AppUserService;
import com.billennium.vaccinationproject.utilities.Headers;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final VisitService visitService;
    private final AppUserService appUserService;

    public UserDto getProfile(){
        UserEntity user = appUserService.getUserFromCurrentSession();
        UserDetailsDto userDetailsDTO = convertToDTO(user.getUserDetails());
        UserDto userDTO = convertToDTO(user);
        userDTO.setUserDetailsDTO(userDetailsDTO);
        return userDTO;
    }

    @Modifying
    public UserDto updateProfile(UserDetailsToUpdateDto userDetailsToUpdateDto){
        UserEntity user = appUserService.getUserFromCurrentSession();
        UserDetailsEntity userDetails = user.getUserDetails();

        userDetails.setCity(userDetailsToUpdateDto.getCity());
        userDetails.setCountry(userDetailsToUpdateDto.getCountry());
        userDetails.setContactPhone(userDetailsToUpdateDto.getContactPhone());
        userDetails.setGender(userDetailsToUpdateDto.getGender());
        userDetails.setDateOfBirth(userDetailsToUpdateDto.getDateOfBirth());
        userDetails.setFirstName(userDetailsToUpdateDto.getFirstName());
        userDetails.setLastName(userDetailsToUpdateDto.getLastName());
        userDetails.setStreet(userDetailsToUpdateDto.getStreet());
        userDetails.setOtherNames(userDetailsToUpdateDto.getOtherNames());
        return convertToDTO(userRepository.save(user));
    }

    public UserDto updateUserById (Long id, UserDetailsToUpdateDto userDetailsToUpdateDto) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("User not found"));
        UserDetailsEntity userDetails = user.getUserDetails();

        userDetails.setCity(userDetailsToUpdateDto.getCity());
        userDetails.setCountry(userDetailsToUpdateDto.getCountry());
        userDetails.setContactPhone(userDetailsToUpdateDto.getContactPhone());
        userDetails.setGender(userDetailsToUpdateDto.getGender());
        userDetails.setDateOfBirth(userDetailsToUpdateDto.getDateOfBirth());
        userDetails.setFirstName(userDetailsToUpdateDto.getFirstName());
        userDetails.setLastName(userDetailsToUpdateDto.getLastName());
        userDetails.setStreet(userDetailsToUpdateDto.getStreet());
        userDetails.setOtherNames(userDetailsToUpdateDto.getOtherNames());
        return convertToDTO(userRepository.save(user));
    }

    public ResponseEntity< List<UserDto>> getUsers(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<UserEntity> userPage = userRepository.findAll(pageable);


        HttpHeaders headers = new HttpHeaders();
        headers.add(Headers.NUMBER_OF_PAGES, String.valueOf(userPage.getTotalPages()));
        headers.add(Headers.NUMBER_OF_ITEMS, String.valueOf(userPage.getTotalElements()));
        headers.add(Headers.CURRENT_PAGE, String.valueOf(page));
        headers.add(Headers.CURRENT_SIZE, String.valueOf(size));


        if(userPage.hasContent()) {
            List<UserDto> list = convertToDTO(userPage.getContent());
            return new ResponseEntity<>(list,headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
        }
    }

    public UserDto getUserById (Long id) {
        return convertToDTO(userRepository.findById(id).orElseThrow(()
                -> new ApiRequestException("User not found")));
    }

    public void cancelVisitById(Long id) {
        UserEntity user = appUserService.getUserFromCurrentSession();
        UserDetailsEntity userDetails = user.getUserDetails();

        VisitEntity foundVisit = userDetails.getVisits().stream().filter(
                visit -> !visit.getTookPlace() && visit.getId().equals( id )
        ).findFirst().orElseThrow(() -> new ApiRequestException("Visit not found", HttpStatus.NOT_FOUND));
        foundVisit.setUserDetails(null);
        userRepository.save(user);
    }

    public UserDetailsDto convertToDTO(UserDetailsEntity userDetails){
        UserDetailsDto userDetailsDTO = modelMapper.map(userDetails, UserDetailsDto.class);
        return userDetailsDTO;
    }

    public UserDto convertToDTO(UserEntity user){
        UserDto userDTO = modelMapper.map(user, UserDto.class);
        userDTO.setUserDetailsDTO(convertToDTO(user.getUserDetails()));
        return userDTO;
    }

    public List<UserDto> convertToDTO(List<UserEntity> users) {
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDetailsEntity convertToEntity(UserDetailsDto userDetailsDTO){
        UserDetailsEntity userDetails = modelMapper.map(userDetailsDTO, UserDetailsEntity.class);
        return userDetails;
    }
}