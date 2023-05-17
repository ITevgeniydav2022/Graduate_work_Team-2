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
public class ResponseWrapperAds {
    /**
     * поле - количество объявлений
     */
    private int count;
    /**
     * поле - коллекция объявлений
     */
    private Collection<AdsDto> results;

    public ResponseWrapperAds(Collection<AdsDto> results) {
        this.count=results.size();
        this.results = results;
    }
}
