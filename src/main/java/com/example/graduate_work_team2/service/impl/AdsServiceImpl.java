package com.example.graduate_work_team2.service.impl;

import com.example.graduate_work_team2.dto.*;
import com.example.graduate_work_team2.entity.Ads;
import com.example.graduate_work_team2.entity.Comment;
import com.example.graduate_work_team2.entity.Image;
import com.example.graduate_work_team2.entity.User;
import com.example.graduate_work_team2.exception.AdsNotFoundException;
import com.example.graduate_work_team2.exception.UserNotFoundException;
import com.example.graduate_work_team2.mapper.AdsMapper;
import com.example.graduate_work_team2.repository.AdsRepository;
import com.example.graduate_work_team2.repository.CommentRepository;
import com.example.graduate_work_team2.repository.ImageRepository;
import com.example.graduate_work_team2.repository.UserRepository;
import com.example.graduate_work_team2.service.AdsService;
import com.example.graduate_work_team2.service.ImageService;
import com.example.graduate_work_team2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Имплементация сервиса для работы с объявлением
 *
 * @author Одокиенко Екатерина
 */
@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final AdsMapper adsMapper;
    private final UserService userService;

    @Override
    public ResponseWrapperAds getAllAdsDto() {
        log.info("Был вызван метод получения всех объявлений");
        Collection<AdsDto> allAds = adsMapper.toDto(adsRepository.findAll());
        return new ResponseWrapperAds(allAds);
    }

    @Override
    public AdsDto addAds(CreateAdsDto createAdsDto, MultipartFile imageFiles) {
        log.info("Был вызван метод добавления объявления");
        Ads newAds = adsMapper.fromDto(createAdsDto);
        newAds.setAuthor(userService.findAuthorizationUser().orElseThrow(UserNotFoundException::new));
        Image newImage = imageService.uploadImage(imageFiles);
        newAds.setImage(newImage);
        adsRepository.save(newAds);
        log.info("Объявление добавлено!");
        return adsMapper.toDto(newAds);
    }

    @Override
    public ResponseWrapperAds getAdsMe() {
        log.info("Был вызван метод получения объявлений авторизованного пользователя");
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        Collection<Ads> adsList = adsRepository.findAll();
        Collection<Ads> user_sAds = adsList.stream().filter(x -> x.getAuthor().equals(user)).toList();
        return new ResponseWrapperAds(adsMapper.toDto(user_sAds));
    }


    @Override
    public FullAdsDto getFullAdsDto(Long adsId) {
        log.info("Был вызван метод получения всей информации по объявлению");
        return adsMapper.toFullAdsDto(adsRepository.findById(adsId).
                orElseThrow(() -> new AdsNotFoundException("Объявление с id " + adsId + " не найдено!")));
    }

    @Override
    public boolean removeAdsById(Long adsId) {
        log.info("Был вызван метод удаления объявления по айди");
        Role role = Role.ADMIN;
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> new AdsNotFoundException("Объявление с id " + adsId + " не найдено!"));
        Optional<User> user = userService.findAuthorizationUser();
        if (ads.getAuthor().getEmail().equals(user.get().getEmail())) {
            List<Long> adsComments = commentRepository.findAll().stream()
                    .filter(comment -> comment.getAd().getId() == ads.getId())
                    .map(Comment::getId)
                    .collect(Collectors.toList());
            commentRepository.deleteAllById(adsComments);
            adsRepository.delete(ads);
            return true;
        }
        return false;
    }

    @Override
    public AdsDto updateAdsDto(Long adsId, CreateAdsDto createAdsDto) {
        log.info("Был вызван метод изменения объявления");
        Ads updatedAds = adsRepository.findById(adsId).orElseThrow(() ->
                new AdsNotFoundException("Объявление с id " + adsId + " не найдено!"));
        User user = userService.findAuthorizationUser().orElseThrow();
        if (updatedAds.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().getAuthority().equals("ADMIN")) {
            updatedAds.setTitle(createAdsDto.getTitle());
            updatedAds.setPrice(createAdsDto.getPrice());
            return adsMapper.toDto(adsRepository.save(updatedAds));
        }
        log.info("Измененное объявление добавлено!");
        throw new UserNotFoundException();
    }
}
