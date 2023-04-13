package com.example.graduate_work_team2.dto;

import lombok.Data;

@Data
public class RegisterReq {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}

