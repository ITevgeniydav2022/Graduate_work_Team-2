package com.example.graduate_work_team2.dto;

import lombok.Data;

/**Класс, описывающий параметры объявлений**/
@Data
public class AdsDto {

    /**id автора объявления**/
    private int author;

    /**ссылка на картинку объявления**/
    private String image;

    /**id объявления**/
    private int pk;

    /**цена объявления**/
    private int price;

    /**заголовок объявления**/
    private String title;

}
