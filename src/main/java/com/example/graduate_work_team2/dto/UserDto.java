package com.example.graduate_work_team2.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
 /**Класс, оптсывающий параметры пользователя**/
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

    /**дата регистрации пользователя**/
    @JsonIgnore
    private String regDate = String.valueOf(LocalDateTime.now());

    /**ссылка на аватар пользователя**/
    private String image;


    public UserDto(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public UserDto(String email, String firstName, String lastName, String phone) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }
}

