package com.example.graduate_work_team2.mapper;


import com.example.graduate_work_team2.dto.CommentDto;
import com.example.graduate_work_team2.dto.CreateUserDto;
import com.example.graduate_work_team2.dto.RegisterReqDto;
import com.example.graduate_work_team2.dto.UserDto;
import com.example.graduate_work_team2.entity.Image;
import com.example.graduate_work_team2.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {User.class, Image.class})
public interface UserMapper {
    String ADS_IMAGES = "/users/image/";
    @Named("imageMapping")
    default String imageMapping(Image image) {
        if (image == null) {
            return null;
        }
        return ADS_IMAGES + image.getId();
    }
    User createUserDtoToEntity(CreateUserDto dto);
    @Mapping(target = "image", source = "image", qualifiedByName = "imageMapping")
    UserDto toUserDto(User entity);
    List<UserDto> toUserDto(Collection<User> entity);
    @Mapping(target = "role", defaultValue = "USER")
    @Mapping(source = "username", target = "email")
    User fromDtoRegReq(RegisterReqDto dto);


//    CreateUserDto toCreateUserDto(User entity);

//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
//
//    @Mapping(source = "email", target = "username")
//    RegisterReqDto toDtoRegReq(User entity);




//    @InheritInverseConfiguration
//    User fromUserDto(UserDto dto);


}
