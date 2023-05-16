package com.example.graduate_work_team2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**Класс, описывающий параметры пользователя**/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    /**id пользователя**/
    private int id;

    /**логин пользователя**/
    private String email;

    /**имя пользователя**/
    private String firstName;

    /**фамилия пользователя**/
    private String lastName;

    /**телефон пользователя**/
    private String phone;

    /**ссылка на аватар пользователя**/
    private String image;
}

