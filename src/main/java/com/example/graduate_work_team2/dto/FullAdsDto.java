package com.example.graduate_work_team2.dto;

import lombok.Data;

@Data
public class FullAdsDto {

    /**id объявления**/
    private int pk;


    /**имя автора объявления**/
    private String authorFirstName;

    /**фамилия автора объявления**/
    private String authorLastName;

    /**описание объявления**/
    private String description;

    /**логин автора объявления**/
    private String email;

    /**ссылка на картинку объявления**/
    private String image;

    /**телефон автора объявления**/
    private String phone;

    /**цена объявления**/
    private int price;

    /**заголовок объявления**/
    private String title;

}
