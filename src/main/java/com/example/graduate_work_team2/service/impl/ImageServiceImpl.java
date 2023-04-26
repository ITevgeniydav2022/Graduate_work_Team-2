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
    private final UserRepository userRepository;
    private final AdsMapper adsMapper;

    @Override
    public Image uploadImage(MultipartFile imageFile) {
        Image image = new Image();
        try {
            image.setData(imageFile.getBytes());
        } catch (IOException e) {
            log.error("Ошибка при попытке сохранить изображение. " + e.getMessage());
            throw new RuntimeException(e);
        }
        image.setFileSize(imageFile.getSize());
        image.setMediaType(imageFile.getContentType());
        return imageRepository.save(image);
    }

    @Override
    public AdsDto updateImage(MultipartFile imageFile, Authentication authentication, long adsId) throws IOException {
        Ads ads = adsRepository.findById(adsId).orElseThrow(() ->
                new AdsNotFoundException("Объявление с id " + adsId + " не найдено!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (ads.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().getAuthority().equals("ADMIN")) {
            Image updatedImage = imageRepository.findByAdsId(adsId);
            Path filePath = Path.of(updatedImage.getFilePath());
//            уточнить, правильно ли сохранение файла???????
            byte[] data = updatedImage.getData();
            Files.deleteIfExists(filePath);
            try {
                Files.write(filePath, data);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            try (Files.write(filePath,imageFile.getBytes(),))
//                    InputStream is = imageFile.getInputStream();
//                    OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
//                    BufferedInputStream bis = new BufferedInputStream(is, 1024);
//                    BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
//            ) {
////                bis.transferTo(bos);
//            }
            updatedImage.setFileSize(imageFile.getSize());
            updatedImage.setMediaType(imageFile.getContentType());
            updatedImage.setData(imageFile.getBytes());
            ads.setImage(imageRepository.save(updatedImage));
            adsRepository.save(ads);
        }
        return adsMapper.toAdsDto(ads);
    }

    @Override
    public Image getImageById(long id) {
        log.info("Был вызван метод получения изображения по id пользователя. ");
        return imageRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Картинка с id " + id + " не найдена!"));
    }

    @Override
    public void removeImage(long id) {
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
