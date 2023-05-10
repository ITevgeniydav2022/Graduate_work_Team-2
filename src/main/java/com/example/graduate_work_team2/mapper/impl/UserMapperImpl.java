package com.example.graduate_work_team2.mapper.impl;

import com.example.graduate_work_team2.dto.CreateUserDto;
import com.example.graduate_work_team2.dto.RegisterReqDto;
import com.example.graduate_work_team2.dto.UserDto;
import com.example.graduate_work_team2.entity.User;
import com.example.graduate_work_team2.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User entity) {
        UserDto userDto = new UserDto();
        userDto.setId(Math.toIntExact(entity.getId()));
        userDto.setEmail(entity.getEmail());
        userDto.setFirstName(entity.getFirstName());
        userDto.setLastName(entity.getLastName());
        userDto.setPhone(entity.getPhone());
        Optional.ofNullable(entity.getImage()).ifPresent(image -> userDto.setImage(
                "/users/" + entity.getImage().getId() + "/image"));
        return userDto;
    }

    @Override
    public User fromDto(UserDto dto) {
        User mappedUser = new User();
        mappedUser.setId(dto.getId());
        mappedUser.setEmail(dto.getEmail());
        mappedUser.setFirstName(dto.getFirstName());
        mappedUser.setLastName(dto.getLastName());
        mappedUser.setPhone(dto.getPhone());
        mappedUser.getImage().setId(Long.parseLong(dto.getImage()));
        return mappedUser;
    }

    @Override
    public User fromDto(RegisterReqDto dto) {
        User mappedUser = new User();
        mappedUser.setPassword(dto.getPassword());
        mappedUser.setFirstName(dto.getFirstName());
        mappedUser.setLastName(dto.getLastName());
        mappedUser.setPhone(dto.getPhone());
        mappedUser.setRole(dto.getRole());
        mappedUser.setEmail(dto.getUsername());
        return mappedUser;
    }

    @Override
    public CreateUserDto toCreateUserDto(User entity) {
        CreateUserDto mappedUserDto = new CreateUserDto();
        mappedUserDto.setEmail(entity.getEmail());
        mappedUserDto.setFirstName(entity.getFirstName());
        mappedUserDto.setLastName(entity.getLastName());
        mappedUserDto.setPassword(entity.getPassword());
        mappedUserDto.setPhone(entity.getPhone());
        return mappedUserDto;
    }

    @Override
    public User createUserDtoToEntity(CreateUserDto dto) {
        User mappedUser = new User();
        mappedUser.setFirstName(dto.getFirstName());
        mappedUser.setLastName(dto.getLastName());
        mappedUser.setPassword(dto.getPassword());
        mappedUser.setPhone(dto.getPhone());
        mappedUser.setEmail(dto.getEmail());
        return mappedUser;
    }
}
