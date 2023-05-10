package com.example.graduate_work_team2.service.impl;

import com.example.graduate_work_team2.dto.AdsDto;
import com.example.graduate_work_team2.entity.Ads;
import com.example.graduate_work_team2.entity.Image;
import com.example.graduate_work_team2.entity.User;
import com.example.graduate_work_team2.exception.AdsNotFoundException;
import com.example.graduate_work_team2.mapper.AdsMapper;
import com.example.graduate_work_team2.repository.AdsRepository;
import com.example.graduate_work_team2.repository.ImageRepository;
import com.example.graduate_work_team2.repository.UserRepository;
import com.example.graduate_work_team2.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Имплементация сервиса для работы с фото в объявлении
 *
 * @author Одокиенко Екатерина
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final AdsRepository adsRepository;

    @Override
    public Image uploadImage(MultipartFile imageFile) {
        Image image = new Image();
        try {
            byte[] bytes = imageFile.getBytes();
            image.setData(bytes);
        } catch (IOException e) {
            log.error("Ошибка при попытке сохранить изображение. " + e.getMessage());
            throw new RuntimeException(e);
        }
        image.setFileSize(imageFile.getSize());
        image.setMediaType(imageFile.getContentType());

        return imageRepository.save(image);
    }

    @Override
    public void updateImageAdsDto(Long adsId, MultipartFile imageFile) {
        Ads ads = adsRepository.findById(adsId).orElseThrow(() ->
                new AdsNotFoundException("Объявление с id " + adsId + " не найдено!"));
       Image imageBefore = ads.getImage();
        try {
            byte[] bytes = imageFile.getBytes();
            imageBefore.setFilePath(Arrays.toString(bytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image imageSaved = imageRepository.saveAndFlush(imageBefore);
        ads.setImage(imageSaved);
        adsRepository.save(ads);
    }

    @Override
    public byte[] getImageById(Long id) {
        log.info("Был вызван метод получения изображения по id пользователя. ");
        Image image =  imageRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Картинка с id " + id + " не найдена!"));
        return image.getData();
    }

    @Override
    public void removeImage(Long id) {
        Image images = imageRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Картинка с id " + id + " не найдена!"));
        Path filePath = Path.of(images.getFilePath());
        images.getAds().setImage(null);
        imageRepository.deleteById(id);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("Ошибка удаления изображения" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
