package com.example.graduate_work_team2.controller;

import com.example.graduate_work_team2.dto.UserDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {

    @PatchMapping("/me")
    public UserDto updateUser(@RequestBody UserDto user) {
        return new UserDto();
    }

}
