package com.example.graduate_work_team2.dto;

import lombok.Data;

@Data
public class LoginReqDto {
    /**пароль**/
    private String password;

    /**логин**/
    private String username;
}

