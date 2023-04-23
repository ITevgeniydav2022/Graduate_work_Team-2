package com.example.graduate_work_team2.mapper;

import com.example.graduate_work_team2.dto.UserDto;
import com.example.graduate_work_team2.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);

    @InheritInverseConfiguration
    User fromUserDto(UserDto userDto);

}
