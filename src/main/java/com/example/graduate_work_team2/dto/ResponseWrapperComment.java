package com.example.graduate_work_team2.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

/**
 * Класс обертки для объекта "Комментарий"
 *
 * @author Одокиенко Екатерина
 */
@Data
public class ResponseWrapperComment {
    /**
     * поле - количество комментариев
     */
    private int count;
    /**
     * поле - коллекция комментариев
     */
    private Collection<CommentDto> results;

    public ResponseWrapperComment(Collection<CommentDto> results) {
        this.count=results.size();
        this.results=results;

    }
}
