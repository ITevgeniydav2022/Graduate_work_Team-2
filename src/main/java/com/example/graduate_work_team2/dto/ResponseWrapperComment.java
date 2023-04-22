package com.example.graduate_work_team2.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

/**
 * Класс обертки для объекта "Комментарий"
 *
 * @author Одокиенко Екатерина
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseWrapperComment<T> {
    /**
     * поле - количество комментариев
     */
    private int count;
    /**
     * поле - коллекция комментариев
     */
    private Collection<T> results;
    public static <T> ResponseWrapperComment<T> of(Collection<T> results) {
        ResponseWrapperComment<T> responseWrapperC = new ResponseWrapperComment<>();
        if (results == null) {
            return responseWrapperC;
        }
        responseWrapperC.results = results;
        responseWrapperC.count = results.size();
        return responseWrapperC;
    }
}
