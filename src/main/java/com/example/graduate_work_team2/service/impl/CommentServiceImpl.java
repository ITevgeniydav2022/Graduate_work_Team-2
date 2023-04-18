package com.example.graduate_work_team2.service.impl;

import com.example.graduate_work_team2.dto.CommentDto;
import com.example.graduate_work_team2.entity.Comment;
import com.example.graduate_work_team2.entity.User;
import com.example.graduate_work_team2.repository.AdsRepository;
import com.example.graduate_work_team2.repository.CommentRepository;
import com.example.graduate_work_team2.repository.UserRepository;
import com.example.graduate_work_team2.service.CommentService;
import com.example.graduate_work_team2.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
/**
 * Имплементация сервиса для работы с комментариями
 * @author Одокиенко Екатерина
 */
@Transactional
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final AdsMapper adsMapper;
    private final CommentMapper commentMapper;
    @Override
    public CommentDto updateComment(long adsId, long comId, CommentDto updateComment, Authentication authentication) {
        Comment updatedComment = commentRepository.findById(adsId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + adsId + " не найден!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (updatedComment.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().getAuthority().equals("ADMIN")) {
            if (updatedComment.getAd().getId() != adsId) {
                throw new NotFoundException("Комментарий с id " + comId + " не принадлежит объявлению с id " + adsId);
            }
            updatedComment.setText(updateComment.getText());
            commentRepository.save(updatedComment);
            return commentMapper.toDto(updatedComment);
        }
        return updateComment;

    }
    @Override
    public boolean deleteComment(long adsId, long comId, Authentication authentication) {
        Comment comment = commentRepository.findById(comId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + comId + " не найден!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (comment.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().getAuthority().equals("ADMIN")) {
            if (comment.getAd().getId() != adsId) {
                throw new NotFoundException("Комментарий с id " + comId+ " не принадлежит объявлению с id " + adsId);
            }
            commentRepository.delete(comment);
            return true;
        }
        return false;
    }
    @Override
    public CommentDto addAdsComments(long adsId, CommentDto commentDto, Authentication authentication) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setAuthor(user);
        comment.setAd(adsRepository.findById(adsId).orElseThrow());
        comment.setCreatedAt(Instant.from(LocalDateTime.now()));
        commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }
    @Override
    public Collection<CommentDto> getComments(long adsId) {
        Collection<Comment> commentList = commentRepository.findAllByAdsId(adsId);
        return commentMapper.toDto(commentList);
    }
    @Override
    public CommentDto getAdsComment(long adsId, long id) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setAuthor(user);
        comment.setAd(adsRepository.findById(adsId).orElseThrow());
        comment.setCreatedAt(Instant.from(LocalDateTime.now()));
        commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }
}
