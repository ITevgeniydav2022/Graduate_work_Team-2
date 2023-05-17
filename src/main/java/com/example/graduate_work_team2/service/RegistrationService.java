package com.example.graduate_work_team2.service;

import com.example.graduate_work_team2.dto.RegisterReqDto;
import com.example.graduate_work_team2.dto.Role;

/**
 * Интерфейс сервиса для регистрации пользователя
 * @author Одокиенко Екатерина
 */
public interface RegistrationService {
    /**
     * @param registerReqDto - объект Dto зарегистрированного пользователя
     */
    boolean register(RegisterReqDto registerReqDto,Role role);

}
