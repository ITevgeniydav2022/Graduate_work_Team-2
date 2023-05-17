package com.example.graduate_work_team2.dto;

import lombok.Data;

@Data
public class RegisterReqDto {

    /**логин**/
    private String username;

    /**пароль**/
    private String password;

    /**имя пользователя**/
    private String firstName;

    /**фамилия пользователя**/
    private String lastName;

    /**телефон пользователя**/
    private String phone;

    /**роль пользователя**/
    private Role role;
}

