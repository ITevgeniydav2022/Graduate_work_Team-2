package com.example.graduate_work_team2.dto;

import lombok.Data;

/**Класс, описывающий параметры создания объявления**/
@Data
public class CreateAdsDto {

    /**описание объявления**/
    private String description;

    /**цена объявления**/
    private int price;

    /**заголовок объявления**/
    private String title;
}
