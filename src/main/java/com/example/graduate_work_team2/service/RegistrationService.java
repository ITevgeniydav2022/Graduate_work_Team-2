package com.example.graduate_work_team2.service;

import com.example.graduate_work_team2.entity.User;

/**
 * Интерфейс сервиса для регистрации пользователя
 * @author Одокиенко Екатерина
 */
public interface RegistrationService {
    /**
     * @param user - объект класса "Пользователь"
     */
    void register(User user);
}
