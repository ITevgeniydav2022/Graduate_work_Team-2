package com.example.graduate_work_team2.service.impl;

import com.example.graduate_work_team2.entity.Image;
import com.example.graduate_work_team2.repository.AdsRepository;
import com.example.graduate_work_team2.repository.ImageRepository;
import com.example.graduate_work_team2.repository.UserRepository;
import com.example.graduate_work_team2.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.UUID;

/**
 * Имплементация сервиса для работы с фото в объявлении
 *
 * @author Одокиенко Екатерина
 */
@Slf4j
@RequiredArgsConstructor
//@Transactional
@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    @Override
    public Image uploadImage(MultipartFile imageFile) throws IOException {
        Image image = new Image();
        byte[] bytes = imageFile.getBytes();
        image.setId(UUID.randomUUID().toString());
        return imageRepository.saveAndFlush(image);
    }

    @Override
    public Image updateImage(Image updateImage, MultipartFile newFile) throws IOException {
        byte[] bytes = newFile.getBytes();
        updateImage.setData(bytes);
        return imageRepository.saveAndFlush(updateImage);

    }

    @Override
    public Image getImageById(String id) {
        log.info("Был вызван метод получения изображения по id пользователя. ");
        return imageRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Картинка с id " + id + " не найдена!"));
    }
}
