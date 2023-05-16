package com.example.graduate_work_team2.mapper.impl;

import com.example.graduate_work_team2.dto.RegisterReqDto;
import com.example.graduate_work_team2.dto.UserDto;
import com.example.graduate_work_team2.entity.User;
import com.example.graduate_work_team2.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Slf4j

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User entity) {
        System.out.println("Был вызван метод маппера из User entity в UserDto"  );
        log.info("Был вызван метод маппера из User entity в UserDto");
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
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
        log.info("Был вызван метод маппера из UserDto в User entity");
        User mappedUser = new User();
        mappedUser.setId(dto.getId());
        mappedUser.setEmail(dto.getEmail());
        mappedUser.setFirstName(dto.getFirstName());
        mappedUser.setLastName(dto.getLastName());
        mappedUser.setPhone(dto.getPhone());
        mappedUser.getImage().setId(dto.getImage());
        return mappedUser;
    }

    @Override
    public User fromDto(RegisterReqDto dto) {
        log.info("Был вызван метод маппера из RegisterReqDto в User entity");
        User mappedUser = new User();
        mappedUser.setUsername(dto.getUsername());
        mappedUser.setPassword(dto.getPassword());
        mappedUser.setFirstName(dto.getFirstName());
        mappedUser.setLastName(dto.getLastName());
        mappedUser.setPhone(dto.getPhone());
        mappedUser.setRole(dto.getRole());
        mappedUser.setEmail(dto.getUsername());
        return mappedUser;
    }

}
