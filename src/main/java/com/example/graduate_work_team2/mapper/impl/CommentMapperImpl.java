package com.example.graduate_work_team2.mapper.impl;

import com.example.graduate_work_team2.dto.CommentDto;
import com.example.graduate_work_team2.entity.Comment;
import com.example.graduate_work_team2.mapper.CommentMapper;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
@Component
public class CommentMapperImpl implements CommentMapper{
    public Comment fromDto(CommentDto dto) {
        Comment mappedComment = new Comment();
        mappedComment.setId(dto.getPk());
//        mappedComment.getAuthor().setId(dto.getAuthor());
//        mappedComment.getAuthor().getImage().setImage(dto.getAuthorImage());
//        mappedComment.getAuthor().setFirstName(dto.getAuthorFirstName());
//        mappedComment.setCreatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(dto.getCreatedAt()), ZoneId.systemDefault()));
        mappedComment.setText(dto.getText());
        return mappedComment;
    }
    public CommentDto toDto(Comment entity) {
        CommentDto commentDto = new CommentDto();
        commentDto.setPk(Math.toIntExact(entity.getId()));
        commentDto.setAuthor(Math.toIntExact(entity.getAuthor().getId()));
        Optional.ofNullable(entity.getAuthor().getImage()).ifPresent(image -> commentDto.setAuthorImage(
                "/users/" + entity.getAuthor().getImage().getId() + "/image"));
        commentDto.setAuthorFirstName(entity.getAuthor().getFirstName());
        commentDto.setCreatedAt(entity.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        commentDto.setText(entity.getText());
        return commentDto;
    }
}
