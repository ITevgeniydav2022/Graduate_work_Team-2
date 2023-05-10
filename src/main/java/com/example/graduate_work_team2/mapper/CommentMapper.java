package com.example.graduate_work_team2.mapper;
import com.example.graduate_work_team2.dto.CommentDto;
import com.example.graduate_work_team2.entity.Comment;
/**
 * Интерфейс CommentMapper для класса Comment
 *
 * @author Одокиенко Екатерина
 */
public interface CommentMapper {
    Comment fromDto(CommentDto dto);

    CommentDto toDto(Comment entity);
}
