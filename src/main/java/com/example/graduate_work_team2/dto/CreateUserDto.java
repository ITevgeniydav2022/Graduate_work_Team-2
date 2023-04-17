package com.example.graduate_work_team2.dto;

import lombok.Data;

@Data
public class CreateUserDto {

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private String phone;
}
