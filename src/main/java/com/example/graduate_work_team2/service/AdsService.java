package com.example.graduate_work_team2.service;

import com.example.graduate_work_team2.dto.AdsDto;
import com.example.graduate_work_team2.dto.CreateAdsDto;
import com.example.graduate_work_team2.dto.FullAdsDto;
import com.example.graduate_work_team2.dto.ResponseWrapperAds;
import com.example.graduate_work_team2.entity.Ads;
import org.springframework.security.core.Authentication;
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

     */
    AdsDto addAds(CreateAdsDto createAdsDto, MultipartFile imageFiles);

    /**
     * Метод получения всех объявлений
     *
     * @return Collection<Ads>
     */
    ResponseWrapperAds getAllAdsDto();

    /**
     * Метод получения коллекции объявлений аутентифицированного пользователя.
     *
     * @return Collection<Ads>
     */
   ResponseWrapperAds getAdsMe();

//    /**
//     * Метод получения объявления по его айди
//     *
//     * @param adsId - айди объявления
//     * @return Ads
//     */
//    Ads getAdsById(long adsId);

    /**
     * Метод получения DTO с полной информацией об объекте
     */
    FullAdsDto getFullAdsDto(Long id);

    /**
     * Метод удаления объявления по его айди
     *
     * @param adsId          - айди объявления


     */
    boolean removeAdsById(Long adsId);

    /**
     * Метод редактирования объявления по его айди
     *
     * @param adsId        - айди объявления
     * @param createAdsDto - измененное объявление
     * @return Ads
     */
    AdsDto updateAdsDto(Long adsId, CreateAdsDto createAdsDto);

}
