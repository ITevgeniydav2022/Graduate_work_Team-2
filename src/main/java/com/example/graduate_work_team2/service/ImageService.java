package com.example.graduate_work_team2.service;

import com.example.graduate_work_team2.entity.Image;
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
    Image uploadImage(MultipartFile imageFile) throws IOException;

    /**
     * Метод обновления фото объявления
     *
     * @param file - фото из объявления
     * @param      - айди объявления
     * @throws IOException, если объект не был найден
     */
    Image updateImage(Image updateImage, MultipartFile file) throws IOException;

    /**
     * Метод получения фото по его айди
     *
     * @param id - айди фото
     * @return Images
     */
    Image getImageById(String id);

}
