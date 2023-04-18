package com.example.graduate_work_team2.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseWrapperComment {

    //Общее количество комментариев
    int count;
    List<CommentDto> results;

}
