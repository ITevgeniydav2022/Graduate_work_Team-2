package com.example.graduate_work_team2.service;

import com.example.graduate_work_team2.dto.CommentDto;
import com.example.graduate_work_team2.dto.ResponseWrapperComment;
import org.springframework.security.core.Authentication;


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

    CommentDto updateComment(Integer adsId, Integer comId, CommentDto updateComment, Authentication authentication);

    /**
     * Метод удаления комментария по его айди и айди объявления
     *
     * @param adsId          - айди объявления
     * @param comId          - айди комментария

     * @return возвращает true, если комментарий удалён, иначе false
     */
    boolean deleteCommentDto(Integer adsId, Integer comId);

    /**
     * Метод получения комментария у определенного объявления
     *
     * @param adsId - айди объявления
     * @return Collection<Comment>
     */
   ResponseWrapperComment getCommentsDto(Integer adsId);

    /**
     * Метод добавления комментария в объявлении по айди объявления
     *
     * @param adsId          - айди объявления
     * @param commentDto     - модель Dto комментария с именем автора, датой создания и самим текстом объявления

     * @return Comment
     */
    CommentDto addAdsCommentsDto(Integer adsId, CommentDto commentDto);


}
