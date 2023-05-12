package com.example.graduate_work_team2.service.impl;

import com.example.graduate_work_team2.dto.UserDto;
import com.example.graduate_work_team2.entity.Image;
import com.example.graduate_work_team2.entity.User;
import com.example.graduate_work_team2.exception.UserNotFoundException;
import com.example.graduate_work_team2.mapper.UserMapper;
import com.example.graduate_work_team2.repository.UserRepository;
import com.example.graduate_work_team2.service.ImageService;
import com.example.graduate_work_team2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


/**
 * Имплементация сервиса для работы с пользователем
 *
 * @author Одокиенко Екатерина
 */
@Slf4j
//@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final ImageService imageService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Был вызван метод поиска пользователя по его username");
        return userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь с таким именем: " + username + " не найден!"));
    }

    @Override
    public Optional<User> findAuthorizationUser() {
        log.info("Был вызван метод получения авторизованного пользователя");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correctName = authentication.getName();
        return userRepository.findByEmail(correctName);
    }

    @Override
    public UserDto getUserDto() {
        log.info("Был вызван метод получения пользователя из БД, конвертирующий его в ДТО");
        Optional<User> correctUser = findAuthorizationUser();
        UserDto correctUserDto = new UserDto();
        if (correctUser.isPresent()) {
            correctUserDto = userMapper.toDto(correctUser.get());
        }
        return correctUserDto;
    }

    @Override
    public UserDto updateUserDto(UserDto dto) {
        log.info("Был вызван метод редактирования пользователя");
        Optional<User> user = findAuthorizationUser();
        User correctUser = new User();
        if (user.isPresent()) {
            correctUser = user.get();
            correctUser.setFirstName(dto.getFirstName());
            correctUser.setLastName(dto.getLastName());
            correctUser.setPhone(dto.getPhone());
            userRepository.save(correctUser);
        }
        log.info("Измененный пользователь сохранен");
        return userMapper.toDto(correctUser);
    }


    @Override
    public void updatePassword(String newPassword, String currentPassword) {
        log.info("Был вызван метод редактирования пароля пользователя");
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BadCredentialsException("Пароль введен некорректно!");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        log.info("Пароль сохранен");
    }

    public void updateUserImage(MultipartFile image) throws IOException {
        log.info("Был вызван метод редактирования аватара пользователя");
        User user = findAuthorizationUser().orElseThrow(UserNotFoundException::new);
        Image imageBefore = user.getImage();
        if (imageBefore == null) {
            try {
                Image imageAfter = imageService.uploadImage(image);
                user.setImage(imageAfter);
            } catch (IOException e) {
                log.error("Ошибка при попытке загрузить изображение" + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            Image savedImage = imageService.updateImage(imageBefore,image);
            user.setImage(savedImage);
        }
        userRepository.save(user);
    }

}
