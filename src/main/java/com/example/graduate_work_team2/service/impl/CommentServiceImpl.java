package com.example.graduate_work_team2.service.impl;

import com.example.graduate_work_team2.dto.CommentDto;
import com.example.graduate_work_team2.dto.ResponseWrapperComment;
import com.example.graduate_work_team2.dto.Role;
import com.example.graduate_work_team2.entity.Ads;
import com.example.graduate_work_team2.entity.Comment;
import com.example.graduate_work_team2.entity.User;
import com.example.graduate_work_team2.exception.CommentNotFoundException;
import com.example.graduate_work_team2.mapper.CommentMapper;
import com.example.graduate_work_team2.repository.AdsRepository;
import com.example.graduate_work_team2.repository.CommentRepository;
import com.example.graduate_work_team2.repository.UserRepository;
import com.example.graduate_work_team2.service.AdsService;
import com.example.graduate_work_team2.service.CommentService;
import com.example.graduate_work_team2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Имплементация сервиса для работы с комментариями
 * @author Одокиенко Екатерина
 */
@Slf4j
//@Transactional
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private  final UserService userService;
    private final AdsService adsService;
    private final AdsRepository adsRepository;

    @Override
    public Comment findById(int id) {
        return commentRepository.findById(id).orElse(null);
    }
    @Override
    public CommentDto updateComment(Integer adsId, Integer comId, CommentDto updateComment, Authentication authentication) {
        log.info("Был вызван метод изменения комментария. ");
        Comment updatedComment = commentRepository.findById(comId)
                .orElseThrow(() -> new CommentNotFoundException("Комментарий с id " + adsId + " не найден!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (updatedComment.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().getAuthority().equals("ADMIN")) {
            if (updatedComment.getAds().getId() != adsId) {
                throw new CommentNotFoundException("Комментарий с id " + comId + " не принадлежит объявлению с id " + adsId);
            }
            updatedComment.setText(updateComment.getText());
            commentRepository.save(updatedComment);
            return commentMapper.toDto(updatedComment);
        }
        return updateComment;

    }
    @Override
    public boolean deleteCommentDto(Integer adsId, Integer comId) {
        log.info("Был вызван метод удаления комментария.");
        Role role = Role.ADMIN;
        Comment comment = commentRepository.findById(comId)
                .orElseThrow(() -> new CommentNotFoundException("Комментарий с id " + comId + " не найден!"));
        Optional<User> user = userService.findAuthorizationUser();
        if (comment.getAuthor().getEmail().equals(user.get().getEmail()) || user.get().getRole().getAuthority().equals("ADMIN")) {
            if (comment.getAds().getId() != adsId) {
                throw new CommentNotFoundException("Комментарий с id " + comId+ " не принадлежит объявлению с id " + adsId);
            }
            commentRepository.delete(comment);
            return true;
        }
        return false;
    }
    @Override
    public CommentDto addAdsCommentsDto(Integer adsId, CommentDto commentDto) {
        log.info("Был вызван метод создания комментария.");
        Ads ads = adsRepository.findById(adsId).orElseThrow();
        Comment comment = commentMapper.fromDto(commentDto);
        comment.setAuthor(userService.findAuthorizationUser().orElseThrow());
        comment.setAds(ads);
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }
    @Override
    public ResponseWrapperComment getCommentsDto(Integer adsId) {
        log.info("Был вызван метод получения всех комментариев по id пользователя. ");
        Collection<Comment> commentList = commentRepository.findAll();
        Collection<CommentDto> commentDtoCollection = new ArrayList<>();
        for (Comment comment : commentList) {
            if (adsId.equals(comment.getAds().getId()) )
            {
                commentDtoCollection.add(commentMapper.toDto(comment));
            }
        }
        return new ResponseWrapperComment(commentDtoCollection);
    }

}
