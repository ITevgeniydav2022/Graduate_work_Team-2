package com.example.graduate_work_team2.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Класс исключения AdsNotFoundException.
 *
 * @author Одокиенко Екатерина
 */

/** Spring автоматически возвращает код состояния запроса, если ресурс не найден на сервере */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AdsNotFoundException extends RuntimeException{
    public AdsNotFoundException(String s) {
        super();
    }
}
