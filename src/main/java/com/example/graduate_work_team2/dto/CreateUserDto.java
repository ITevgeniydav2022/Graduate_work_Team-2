package com.example.graduate_work_team2.dto;

import lombok.Data;

/**Класс, описывающий параметры создания пользователя**/
@Data
public class CreateUserDto {

    /**логин пользователя**/
    private String email;

    /**имя пользователя**/
    private String firstName;

    /**фамилия пользователя**/
    private String lastName;

    /**пароль**/
    private String password;

    /**телефон пользователя**/
    private String phone;
}
