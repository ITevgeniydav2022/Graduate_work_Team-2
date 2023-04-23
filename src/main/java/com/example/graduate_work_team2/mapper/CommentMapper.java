package com.example.graduate_work_team2.mapper;

import com.example.graduate_work_team2.dto.CommentDto;
import com.example.graduate_work_team2.entity.Comment;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    CommentDto toCommentDto(Comment entity);

//    @Mapping(target = "author", source = "author.id") -уточнить!!!!!
    @Mapping(source = "id", target = "pk")
    @Mapping(target = "createdAt", source = "entity.createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @InheritInverseConfiguration
    Comment fromCommentDto(CommentDto dto);

    List<CommentDto> toCommentDto(Collection<Comment> entity);

//    List<Comment> fromCommentDto(Collection<CommentDto> dto); - в дальнейшем уточнить!
}

