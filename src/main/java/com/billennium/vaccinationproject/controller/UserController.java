package com.billennium.vaccinationproject.controller;

import com.billennium.vaccinationproject.service.UserService;
import com.billennium.vaccinationproject.dto.UserDetailsToUpdateDto;
import com.billennium.vaccinationproject.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("user/profile")
    @ResponseBody
    public ResponseEntity<UserDto> getProfile(){
        return new ResponseEntity<>(userService.getProfile(), HttpStatus.OK);
    }

    @PutMapping("user/profile")
    @ResponseBody
    public ResponseEntity<UserDto> updateProfile (@RequestBody @Valid UserDetailsToUpdateDto userDetailsToUpdateDto) {
        return new ResponseEntity<>(userService.updateProfile(userDetailsToUpdateDto), HttpStatus.OK);
    }

    @PutMapping("user/profile/cancel/{id}")
    @ResponseBody
    public ResponseEntity<?> cancelVisitById (@PathVariable Long id) {
        userService.cancelVisitById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("admin/users")
    @ResponseBody
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size){
        return userService.getUsers(page, size);
    }

    @GetMapping("admin/users/{id}")
    @ResponseBody
    public ResponseEntity<UserDto> getUser (@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping("admin/users/{id}")
    @ResponseBody
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,
                                              @RequestBody @Valid UserDetailsToUpdateDto userDetailsToUpdateDto) {
        return new ResponseEntity<>(userService.updateUserById(id, userDetailsToUpdateDto), HttpStatus.OK);
    }
}
