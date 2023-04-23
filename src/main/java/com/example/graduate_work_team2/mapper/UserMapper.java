package com.example.graduate_work_team2.mapper;


import com.example.graduate_work_team2.dto.CommentDto;
import com.example.graduate_work_team2.dto.CreateUserDto;
import com.example.graduate_work_team2.dto.RegisterReqDto;
import com.example.graduate_work_team2.dto.UserDto;
import com.example.graduate_work_team2.entity.Comment;
import com.example.graduate_work_team2.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper {
//    позже доработаю
//    CreateUserDto toCreateUserDto(User entity);
//
//    User createUserDtoToEntity(CreateUserDto dto);

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "email", target = "username")
    RegisterReqDto toDtoRegReq(User entity);

    @Mapping(target = "role", defaultValue = "USER")
    @Mapping(source = "username", target = "email")
    User fromDtoRegReq(RegisterReqDto dto);

    UserDto toUserDto(User entity);
    @InheritInverseConfiguration
    User fromUserDto(UserDto dto);

    List<UserDto> toUserDto(Collection<User> entity);

//    List<User> fromUserDto(Collection<UserDto> dto);- в дальнейшем уточнить!

}
