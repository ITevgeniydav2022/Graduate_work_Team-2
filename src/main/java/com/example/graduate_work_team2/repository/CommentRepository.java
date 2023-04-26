package com.example.graduate_work_team2.repository;

import com.example.graduate_work_team2.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * Интерфейс CommentRepository для класса "Комментарий в объявлении"
 * @author Одокиенко Екатерина
 */

/** Механизм для хранения, извлечения, обновления, удаления и поиска объектов. */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * Метод получения списка у объекта класса "Комментарий в объявлении" по айди объявления.
     * @param adsId
     */
    List<Comment> findAllByAdId(Long adsId);

}
