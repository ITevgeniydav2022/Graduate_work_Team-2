package com.example.graduate_work_team2.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

/**
 * Класс обертки для объекта "Объявление"
 *
 * @author Одокиенко Екатерина
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseWrapperAds<T> {
    /**
     * поле - количество объявлений
     */
    private int count;
    /**
     * поле - коллекция объявлений
     */
    private Collection<T> results;

    public static <T> ResponseWrapperAds<T> of(Collection<T> results) {
        ResponseWrapperAds<T> responseWrapperA = new ResponseWrapperAds<>();
        if (results == null) {
            return responseWrapperA;
        }
        responseWrapperA.results = results;
        responseWrapperA.count = results.size();
        return responseWrapperA;
    }

}
