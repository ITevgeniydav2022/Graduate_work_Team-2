package com.example.graduate_work_team2.service;

import com.example.graduate_work_team2.dto.CreateUserDto;
import com.example.graduate_work_team2.dto.Role;
import com.example.graduate_work_team2.dto.UserDto;
import com.example.graduate_work_team2.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

/**
 * Интерфейс сервиса для работы с пользователем
 *
 * @author Одокиенко Екатерина
 */
public interface UserService {


    User getUser(Authentication authentication);

    /**
     * Метод создания пользователя
     *
     * @param createUserDto - объект класса "Пользователь" для передачи данных
     * @return User
     */
    UserDto createUser(CreateUserDto createUserDto);

    /**
     * Метод получения коллекции всех существующих пользователей
     *
     * @return Collection<User>
     */
    Collection<UserDto> getUsers();

    /**
     * Метод получения аутентифицированного пользователя.
     *
     * @return User
     */
    UserDto getUserMe(Authentication authentication);

    /**
     * Метод редактирования пользователя
     *
     * @param user - объект класса "Пользователь"
     * @return User - изменённый пользователь
     */
    UserDto updateUser(UserDto user);

    /**
     * Метод получения пользователя по айди
     *
     * @param id - айди пользователя
     * @return User
     */
    User getUserById(long id);

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
     * @param authentication - данные аутентификации
     */
    String updateUserImage(MultipartFile image, Authentication authentication) throws IOException;

    /**
     * Метод изменения роли пользователя
     *
     * @param id  -  идентификатор пользователя
     * @param role - новая роль
     */
    UserDto updateRole(long id, Role role);
}
