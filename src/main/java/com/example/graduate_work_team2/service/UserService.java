package com.example.graduate_work_team2.service;

import com.example.graduate_work_team2.dto.UserDto;
import com.example.graduate_work_team2.entity.User;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Optional;

/**
 * Интерфейс сервиса для работы с пользователем
 *
 * @author Одокиенко Екатерина
 */
public interface UserService {

    /**
     * Метод получения Dto авторизованного пользователя
     * @return UserDto
     */
    UserDto getUserDto();

    /**
     * Метод редактирования пользователя
     *

     * @return User - изменённый пользователь
     */
    UserDto updateUserDto(UserDto dto);

    /**
     * Метод поиска авторизованного пользователя

     * @return Optional<User>
     */
    Optional<User> findAuthorizationUser();

    /**
     * Метод изменения пароля пользователя
     *
     * @param newPassword     - новый пароль
     * @param currentPassword - старый пароль
     * @return Возвращает true если пароль успешно изменен, иначе false
     */
    void updatePassword(String newPassword, String currentPassword);

    /**
     * Метод изменения аватара пользователя
     *
     * @param image - новое фото
     */
    void updateUserImage(MultipartFile image) throws IOException;

}
