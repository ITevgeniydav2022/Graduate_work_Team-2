package com.example.graduate_work_team2.service.impl;


import com.example.graduate_work_team2.dto.*;
import com.example.graduate_work_team2.entity.Ads;
import com.example.graduate_work_team2.entity.Image;
import com.example.graduate_work_team2.entity.User;
import com.example.graduate_work_team2.exception.AdsNotFoundException;
import com.example.graduate_work_team2.exception.UserNotFoundException;
import com.example.graduate_work_team2.mapper.AdsMapper;
import com.example.graduate_work_team2.repository.AdsRepository;
import com.example.graduate_work_team2.service.AdsService;
import com.example.graduate_work_team2.service.ImageService;
import com.example.graduate_work_team2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;

/**
 * Имплементация сервиса для работы с объявлением
 *
 * @author Одокиенко Екатерина
 */
@Slf4j
//@Transactional
@RequiredArgsConstructor
@Service
public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;
    private final ImageService imageService;
    private final AdsMapper adsMapper;
    private final UserService userService;


    @Override
    public Ads findById(int id) {
        return adsRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseWrapperAds getAllAdsDto() {
        log.info("Был вызван метод получения всех объявлений");
        Collection<AdsDto> allAds = adsMapper.toDto(adsRepository.findAll());
        return new ResponseWrapperAds(allAds);
    }

    @Override
    public AdsDto addAds(CreateAdsDto createAdsDto, MultipartFile imageFiles) throws IOException {
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
        User user = userService.findAuthorizationUser().orElseThrow(UserNotFoundException::new);
        Collection<Ads> adsList = adsRepository.findAll();
        Collection<Ads> user_sAds = adsList.stream().filter(x -> x.getAuthor().equals(user)).toList();
        return new ResponseWrapperAds(adsMapper.toDto(user_sAds));
    }


    @Override
    public FullAdsDto getFullAdsDto(Integer adsId) {
        log.info("Был вызван метод получения всей информации по объявлению");
        return adsMapper.toFullAdsDto(adsRepository.findById(adsId).
                orElseThrow(() -> new AdsNotFoundException("Объявление с id " + adsId + " не найдено!")));
    }

    @Override
    public boolean removeAdsById(Integer adsId) {
        log.info("Был вызван метод удаления объявления по айди");
        Role role = Role.ADMIN;
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> new AdsNotFoundException("Объявление с id " + adsId + " не найдено!"));
        Optional<User> user = userService.findAuthorizationUser();
        User notEmptyUser = user.get();
        String correctName = notEmptyUser.getUsername();
        if (ads.getAuthor().getUsername().equals(correctName) || notEmptyUser.getAuthorities().contains(role)) {
            adsRepository.delete(ads);
            return true;
        }
        log.info("Удаление прошло успешно!");
        throw new UserNotFoundException();
    }

    @Override
    public AdsDto updateAdsDto(Integer adsId, CreateAdsDto createAdsDto) {
        log.info("Был вызван метод изменения объявления");
        Ads updatedAds = adsRepository.findById(adsId).orElseThrow(() ->
                new AdsNotFoundException("Объявление с id " + adsId + " не найдено!"));
        User user = userService.findAuthorizationUser().orElseThrow();
        if (updatedAds.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().getAuthority().equals("ADMIN")) {
            updatedAds.setTitle(createAdsDto.getTitle());
            updatedAds.setPrice(createAdsDto.getPrice());
            updatedAds.setDescription(createAdsDto.getDescription());
            return adsMapper.toDto(adsRepository.save(updatedAds));
        }
        log.info("Измененное объявление добавлено!");
        throw new UserNotFoundException();
    }

    @Override
    public void updateAdsImage(Integer id, MultipartFile adsFile) throws IOException {
        Ads ads = adsRepository.findById(id).orElseThrow(() ->
                new AdsNotFoundException("Объявление с id " + id + " не найдено!"));
        Image imageUpdate = imageService.updateImage(ads.getImage(), adsFile);
        ads.setImage(imageUpdate);
        adsRepository.save(ads);
    }
}
