package com.example.graduate_work_team2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Класс исключения UserNotFoundException.
 *
 * @author Одокиенко Екатерина
 */

/** Spring автоматически возвращает код состояния запроса, если ресурс не найден на сервере */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {

        super("У авторизованного пользователя нет доступа к ресурсу!");
    }
}
