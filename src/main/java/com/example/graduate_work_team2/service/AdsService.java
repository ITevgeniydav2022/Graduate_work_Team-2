package com.example.graduate_work_team2.service;

import com.example.graduate_work_team2.dto.AdsDto;
import com.example.graduate_work_team2.dto.CommentDto;
import com.example.graduate_work_team2.dto.CreateAdsDto;
import com.example.graduate_work_team2.dto.FullAdsDto;
import com.example.graduate_work_team2.entity.Ads;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

/**
 * Интерфейс сервиса для работы с объявлениями
 *
 * @author Одокиенко Екатерина
 */

public interface AdsService {
    /**
     * Метод добавления объявления
     *
     * @param createAdsDto - модель Dto объявления с заголовком и ценой
     * @param imageFiles   - фото объявления
     * @return Ads
     * @throws IOException, если объект не был найден
     */
    AdsDto addAds(CreateAdsDto createAdsDto, MultipartFile imageFiles) throws IOException;

    /**
     * Метод получения всех объявлений
     *
     * @return Collection<Ads>
     */
    Collection<AdsDto> getAllAds();

    /**
     * Метод получения коллекции объявлений аутентифицированного пользователя.
     *
     * @return Collection<Ads>
     */
    Collection<AdsDto> getAdsMe(Authentication authentication);

    /**
     * Метод получения объявления по его айди
     *
     * @param adsId - айди объявления
     * @return Ads
     */
    Ads getAdsById(long adsId);

    /**
     * Метод получения DTO с полной информацией об объекте
     */
    FullAdsDto getFullAdsDto(long id);

    /**
     * Метод удаления объявления по его айди
     *
     * @param adsId          - айди объявления
     * @param authentication - данные аутентификации
     * @return ResponseEntity<Void>
     */
    boolean removeAdsById(Long adsId, Authentication authentication) throws IOException;

    /**
     * Метод редактирования объявления по его айди
     *
     * @param adsId        - айди объявления
     * @param updateAdsDto - измененное объявление
     * @return Ads
     */
    AdsDto updateAds(Long adsId, AdsDto updateAdsDto, Authentication authentication);

}
