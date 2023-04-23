package com.example.graduate_work_team2.mapper;

import com.example.graduate_work_team2.dto.CommentDto;
import com.example.graduate_work_team2.entity.Comment;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDto toCommentDto(Comment comment);

    @InheritInverseConfiguration
    Comment fromCommentDto(CommentDto commentDto);
}

