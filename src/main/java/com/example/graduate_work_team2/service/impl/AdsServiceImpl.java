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
        return adsMapper.toAdsDto(adsRepository.findAll());
    }
    @Override
    public AdsDto addAds(CreateAdsDto createAdsDto, MultipartFile imageFiles) throws IOException {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        Ads ads = adsMapper.fromAdsDto(createAdsDto);
        ads.setAuthor(user);
        ads.setImage(imageService.uploadImage(imageFiles));
        return adsMapper.toAdsDto(adsRepository.save(ads));
    }

    @Override
    public Collection<AdsDto> getAdsMe(Authentication authentication) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        Collection<Ads> adsList = adsRepository.findAllByAuthorId(user.getId());
        return adsMapper.toAdsDto(adsList);
    }

    @Override
    public Ads getAdsById(long adsId) {
        return adsRepository.findById(adsId).orElseThrow(() ->
                new AdsNotFoundException("Объявление с id " + adsId + " не найдено!"));
    }

    @Override
    public FullAdsDto getFullAdsDto(long adsId) {
        return adsMapper.toFullAdsDto(adsRepository.findById(adsId).
                orElseThrow(() -> new AdsNotFoundException("Объявление с id " + adsId + " не найдено!")));
    }

    @Override
    public boolean removeAdsById(Long adsId, Authentication authentication) throws IOException {
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> new AdsNotFoundException("Объявление с id " + adsId + " не найдено!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (ads.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().getAuthority().equals("ADMIN")) {
            List<Long> adsComments = commentRepository.findAll().stream()
                    .filter(comment -> comment.getAd().getId() == ads.getId())
                    .map(Comment::getId)
                    .collect(Collectors.toList());
            commentRepository.deleteAllById(adsComments);
            imageService.removeImage(ads.getImage().getId());
            adsRepository.delete(ads);
            return true;
        }
        return false;
    }
    @Override
    public AdsDto updateAds(Long adsId, AdsDto updateAdsDto, Authentication authentication) {
        Ads updatedAds = adsRepository.findById(adsId).orElseThrow(() ->
                new AdsNotFoundException("Объявление с id " + adsId + " не найдено!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (updatedAds.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().getAuthority().equals("ADMIN")) {
            updatedAds.setTitle(updateAdsDto.getTitle());
            updatedAds.setPrice(updateAdsDto.getPrice());
            adsRepository.save(updatedAds);
            return adsMapper.toAdsDto(updatedAds);
        }
        return updateAdsDto;
    }
}
