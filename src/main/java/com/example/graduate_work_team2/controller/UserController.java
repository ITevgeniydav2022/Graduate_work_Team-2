package com.example.graduate_work_team2.controller;

import com.example.graduate_work_team2.dto.UserDto;
import com.example.graduate_work_team2.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class UserController {


    private final AuthService authService;

    @PatchMapping("/users/me")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        System.out.println("Hello");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        System.out.println(authentication.isAuthenticated());
        return new UserDto(userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getPhone());
    }


    @GetMapping("/users/me")
    public UserDto getUser() {
        //    UserDto userDto = authService.getUser();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        System.out.println(authentication.isAuthenticated());
        return new UserDto("Test", "test", "12345");
    }

}