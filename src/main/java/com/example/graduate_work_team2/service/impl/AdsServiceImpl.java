package com.example.graduate_work_team2.service.impl;

import com.example.graduate_work_team2.dto.AdsDto;
import com.example.graduate_work_team2.dto.CreateAdsDto;
import com.example.graduate_work_team2.dto.FullAdsDto;
import com.example.graduate_work_team2.entity.Ads;
import com.example.graduate_work_team2.entity.Comment;
import com.example.graduate_work_team2.entity.User;
import com.example.graduate_work_team2.exception.AdsNotFoundException;
import com.example.graduate_work_team2.mapper.AdsMapper;
import com.example.graduate_work_team2.repository.AdsRepository;
import com.example.graduate_work_team2.repository.CommentRepository;
import com.example.graduate_work_team2.repository.UserRepository;
import com.example.graduate_work_team2.service.AdsService;
import com.example.graduate_work_team2.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Имплементация сервиса для работы с объявлением
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
    private final AdsMapper adsMapper;
    @Override
    public Collection<AdsDto> getAllAds() {
        log.info("Был вызван метод получения всех объявлений");
        return adsMapper.toAdsDto(adsRepository.findAll());
    }
    @Override
    public AdsDto addAds(CreateAdsDto createAdsDto, MultipartFile imageFiles) throws IOException {
        log.info("Был вызван метод добавления объявления");
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        Ads ads = adsMapper.fromAdsDto(createAdsDto);
        ads.setAuthor(user);
        ads.setImage(imageService.uploadImage(imageFiles));
        log.info("Объявление добавлено!");
        return adsMapper.toAdsDto(adsRepository.save(ads));
    }

    @Override
    public Collection<AdsDto> getAdsMe(Authentication authentication) {
        log.info("Был вызван метод авторизованного пользователя");
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        Collection<Ads> adsList = adsRepository.findAllByAuthorId(user.getId());
        return adsMapper.toAdsDto(adsList);
    }

    @Override
    public Ads getAdsById(long adsId) {
        log.info("Был вызван метод получения объявления по айди!");
        return adsRepository.findById(adsId).orElseThrow(() ->
                new AdsNotFoundException("Объявление с id " + adsId + " не найдено!"));
    }

    @Override
    public FullAdsDto getFullAdsDto(long adsId) {
        log.info("Был вызван метод получения всей информации по объявлению");
        return adsMapper.toFullAdsDto(adsRepository.findById(adsId).
                orElseThrow(() -> new AdsNotFoundException("Объявление с id " + adsId + " не найдено!")));
    }

    @Override
    public boolean removeAdsById(Long adsId, Authentication authentication) {
        log.info("Был вызван метод удаления объявления по айди");
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> new AdsNotFoundException("Объявление с id " + adsId + " не найдено!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (ads.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().getAuthority().equals("ADMIN")) {
            List<Long> adsComments = commentRepository.findAll().stream()
                    .filter(comment -> comment.getAd().getId() == ads.getId())
                    .map(Comment::getId)
                    .collect(Collectors.toList());
            commentRepository.deleteAllById(adsComments);
            try {
                imageService.removeImage(ads.getImage().getId());
            } catch (IOException e) {
                log.error("Ошибка при попытке удаления изображения в объявлении " + e.getMessage());
                throw new RuntimeException(e);
            }
            adsRepository.delete(ads);
            return true;
        }
        return false;
    }
    @Override
    public AdsDto updateAds(Long adsId, AdsDto updateAdsDto, Authentication authentication) {
        log.info("Был вызван метод изменения объявления");
        Ads updatedAds = adsRepository.findById(adsId).orElseThrow(() ->
                new AdsNotFoundException("Объявление с id " + adsId + " не найдено!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (updatedAds.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().getAuthority().equals("ADMIN")) {
            updatedAds.setTitle(updateAdsDto.getTitle());
            updatedAds.setPrice(updateAdsDto.getPrice());
            adsRepository.save(updatedAds);
            log.info("Измененное объявление добавлено!");
            return adsMapper.toAdsDto(updatedAds);
        }
        return updateAdsDto;
    }
}
