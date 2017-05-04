package com.hazardmanager.users.controllers;

import com.hazardmanager.users.DTO.CreatingUserDto;
import com.hazardmanager.users.DTO.UserDto;
import com.hazardmanager.users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hazardmanager.users.services.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = this.service.getAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<UserDto> result = new ArrayList<>();

        for (User user : users) {
            UserDto dto = toDto(user);
            result.add(dto);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDto> addUser(@RequestBody CreatingUserDto userDto) {
        User user = toCreatingModel(userDto);
        User savedUser = this.service.save(user);
        return new ResponseEntity<>(toDto(savedUser), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") String id) {
        User user = this.service.getById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(toDto(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserDto> modifyUser(@RequestBody CreatingUserDto userDto, @PathVariable("id") String id) {
        User user = this.service.getById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        modifyUserAccordingToDTO(user, userDto);
        this.service.save(user);
        return new ResponseEntity<>(toDto(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") String id){
        User user = this.service.getById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        this.service.delete(user.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    private void modifyUserAccordingToDTO(User user, CreatingUserDto userDto) {
        if (userDto.firstName != null) {
            user.setFirstName(userDto.firstName);
        }
        if (userDto.lastName != null) {
            user.setLastName(userDto.lastName);
        }
        if (userDto.userName != null) {
            user.setUserName(userDto.userName);
        }
        if (userDto.password != null) {
            user.setPassword(userDto.password);
        }
        if (userDto.email != null) {
            user.setEmail(userDto.email);
        }
        if (userDto.phoneNumber != null) {
            user.setPhoneNumber(userDto.phoneNumber);
        }
    }

    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.id = user.getId();
        dto.firstName = user.getFirstName();
        dto.lastName = user.getLastName();
        dto.userName = user.getUserName();
        dto.password = user.getPassword();
        dto.email = user.getEmail();
        dto.phoneNumber = user.getPhoneNumber();
        return dto;
    }

    private User toCreatingModel(CreatingUserDto dto) {
        User user = new User();
        user.setFirstName(dto.firstName);
        user.setLastName(dto.lastName);
        user.setUserName(dto.userName);
        user.setPassword(dto.password);
        user.setPhoneNumber(dto.phoneNumber);
        user.setEmail(dto.email);
        return user;
    }
}
