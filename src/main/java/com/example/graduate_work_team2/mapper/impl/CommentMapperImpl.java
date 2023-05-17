package com.example.graduate_work_team2.mapper.impl;

import com.example.graduate_work_team2.dto.CommentDto;
import com.example.graduate_work_team2.entity.Comment;
import com.example.graduate_work_team2.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Optional;
@Slf4j
@Component
public class CommentMapperImpl implements CommentMapper{
    public Comment fromDto(CommentDto dto) {
        log.info("Был вызван метод маппера из CommentDto в Comment entity");
        Comment mappedComment = new Comment();
        mappedComment.setId(dto.getPk());
        mappedComment.setText(dto.getText());
        return mappedComment;
    }
    public CommentDto toDto(Comment entity) {
        log.info("Был вызван метод маппера из Comment entity в CommentDto");
        CommentDto commentDto = new CommentDto();
        commentDto.setPk(entity.getId());
        commentDto.setAuthor(entity.getAuthor().getId());
        Optional.ofNullable(entity.getAuthor().getImage()).ifPresent(image -> commentDto.setAuthorImage(
                "/comments/" + entity.getId() + "/image"));
        commentDto.setAuthorFirstName(entity.getAuthor().getFirstName());
        commentDto.setCreatedAt(entity.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        commentDto.setText(entity.getText());
        return commentDto;
    }
}
