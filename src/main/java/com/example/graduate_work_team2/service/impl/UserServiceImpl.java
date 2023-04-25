package com.example.graduate_work_team2.service.impl;

import com.example.graduate_work_team2.dto.CreateUserDto;
import com.example.graduate_work_team2.dto.Role;
import com.example.graduate_work_team2.dto.UserDto;
import com.example.graduate_work_team2.entity.User;
import com.example.graduate_work_team2.mapper.UserMapper;
import com.example.graduate_work_team2.repository.UserRepository;
import com.example.graduate_work_team2.service.ImageService;
import com.example.graduate_work_team2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;


import javax.validation.ValidationException;
import java.io.IOException;
import java.util.Collection;

import static com.example.graduate_work_team2.dto.Role.USER;

/**
 * Имплементация сервиса для работы с пользователем
 *
 * @author Одокиенко Екатерина
 */
@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final ImageService imageService;

//позже будет дорабатываться
//    @Override
//    public UserDto createUser(CreateUserDto createUserDto) {
//        if (userRepository.existsByEmail(user.getEmail())) {
//
//            throw new ValidationException(String.format("Пользователь \"%s\" уже существует!", user.getEmail()));
//        }
//        User createdUser = userMapper.createUserDtoToEntity(createUserDto);
//        if (createdUser.getRole() == null) {
//            createdUser.setRole(USER);
//        }
//        return userMapper.toDtoRegReq(userRepository.save(createdUser));
//    }

    @Override
    public Collection<UserDto> getUsers() {
        return userMapper.toUserDto(userRepository.findAll());
    }

    @Override
    public UserDto getUserMe(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto updatedUserDto) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        user.setFirstName(updatedUserDto.getFirstName());
        user.setLastName(updatedUserDto.getLastName());
        user.setPhone(updatedUserDto.getPhone());
        userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден!"));
    }
    @Override
    public void updatePassword(String newPassword, String currentPassword) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BadCredentialsException("Пароль введен некорректно!");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    public String updateUserImage(MultipartFile image, Authentication authentication) {
        User user = getUserByUsername(authentication.getName());
        if (user.getImage() != null) {
            try {
                imageService.removeImage(user.getId());
            } catch (IOException e) {
                log.error("Ошибка при попытке удалить изображение" + e.getMessage());
                throw new RuntimeException(e);
            }
        }
        try {
            user.setImage(imageService.uploadImage(image));
        } catch (IOException e) {
            log.error("Ошибка при попытке загрузить изображение" + e.getMessage());
            throw new RuntimeException(e);
        }
        return "/users/image/" + userRepository.save(user).getImage().getId();
    }
    private User getUserByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Пользователь с таким email: \"%s\" не найден", username)));
    }
    @Override
    public UserDto updateRole(long id, Role role) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Пользователь с id " + id + " не найден!"));
        user.setRole(role);
        userRepository.save(user);
        return userMapper.toUserDto(user);
    }
}
