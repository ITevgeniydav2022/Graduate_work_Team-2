package com.example.graduate_work_team2.service;

import com.example.graduate_work_team2.dto.CommentDto;
import com.example.graduate_work_team2.entity.Comment;
import org.springframework.security.core.Authentication;

import java.util.Collection;

/**
 * Интерфейс сервиса для работы с комментариями
 *
 * @author Одокиенко Екатерина
 */
public interface CommentService {
    /**
     * Метод редактирования комментария по айди
     *
     * @param comId          - айди комментария
     * @param adsId          - айди объявления
     * @param authentication - данные аутентификации
     * @param updateComment  - измененный комментарий
     * @return Comment
     */

    CommentDto updateComment(long adsId, long comId, CommentDto updateComment, Authentication authentication);

    /**
     * Метод удаления комментария по его айди и айди объявления
     *
     * @param adsId          - айди объявления
     * @param comId          - айди комментария
     * @param authentication - данные аутентификации
     * @return возвращает true, если комментарий удалён, иначе false
     */
    boolean deleteComment(long adsId, long comId, Authentication authentication);

    /**
     * Метод получения коллекции комментариев у определенного объявления
     *
     * @param adsId - айди объявления
     * @return Collection<Comment>
     */
    Collection<CommentDto> getComments(long adsId);

    /**
     * Метод добавления комментария в объявлении
     *
     * @param adsId          - айди объявления
     * @param commentDto     - модель Dto комментария с именем автора, датой создания и самим текстом объявления
     * @param authentication - данные аутентификации
     * @return Comment
     */
    Comment addAdsComments(long adsId, CommentDto commentDto, Authentication authentication);

//    ????пока возможно и не понадобится
//    /**
//     * Метод получения комментария по его айди
//     *
//     * @param id    - айди комментария
//     * @param adsId - айди объявления
//     * @return Comment
//     */
//    CommentDto getAdsComment(long adsId, long id);

}
