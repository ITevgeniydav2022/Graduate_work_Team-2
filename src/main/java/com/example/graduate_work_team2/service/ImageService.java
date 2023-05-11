package com.example.graduate_work_team2.service;

import com.example.graduate_work_team2.dto.AdsDto;

import com.example.graduate_work_team2.entity.Image;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Интерфейс сервиса для работы с фото в объявлениях
 *
 * @author Одокиенко Екатерина
 */
public interface ImageService {
    /**
     * Метод сохранения фото в БД
     *
     * @param imageFile - фото из объявления
     * @return Images
     */
    Image uploadImage(MultipartFile imageFile);

    /**
     * Метод обновления фото объявления
     *
     * @param imageFile - фото из объявления
     * @param adsId     - айди объявления
     * @throws IOException, если объект не был найден
     */
    void updateImageAdsDto(Long adsId, MultipartFile imageFile);

    /**
     * Метод получения фото по его айди
     *
     * @param id - айди фото
     * @return Images
     */
    byte[] getImageById(Long id);

    /**
     * Метод удаления фото по айди
     *
     * @param id - айди фото
     * @throws IOException, если объект не был найден
     */
    void removeImage(Long id) throws IOException;
}
