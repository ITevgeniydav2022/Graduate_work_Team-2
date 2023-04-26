package com.example.graduate_work_team2.mapper;

import com.example.graduate_work_team2.dto.CommentDto;
import com.example.graduate_work_team2.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",uses = {Comment.class})
public interface CommentMapper {

    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "createdAt", ignore = true)
    CommentDto toCommentDto(Comment entity);

    @Mapping(target = "author", ignore = true)
//    @Mapping(target = "id", source = "pk")
    @Mapping(target = "createdAt", ignore = true)
    Comment fromCommentDto(CommentDto dto);

    List<CommentDto> toCommentDto(Collection<Comment> entity);


//    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
}

