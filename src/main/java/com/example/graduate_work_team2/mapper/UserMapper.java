package com.example.graduate_work_team2.mapper;

import com.example.graduate_work_team2.dto.RegisterReqDto;
import com.example.graduate_work_team2.dto.UserDto;
import com.example.graduate_work_team2.entity.User;

/**
 * Интерфейс UserMapper для класса User
 *
 * @author Одокиенко Екатерина
 */
public interface UserMapper {
    UserDto toDto(User entity);

    User fromDto(UserDto dto);

    User fromDto(RegisterReqDto dto);

}
