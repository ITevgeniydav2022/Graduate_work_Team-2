package com.example.graduate_work_team2.service.impl;

import com.example.graduate_work_team2.dto.CreateUserDto;
import com.example.graduate_work_team2.dto.Role;
import com.example.graduate_work_team2.dto.UserDto;
import com.example.graduate_work_team2.entity.User;
import com.example.graduate_work_team2.repository.UserRepository;
import com.example.graduate_work_team2.service.ImageService;
import com.example.graduate_work_team2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;


import javax.validation.ValidationException;
import java.io.IOException;
import java.util.Collection;
/**
 * Имплементация сервиса для работы с пользователем
 *
 * @author Одокиенко Екатерина
 */
@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final User user;
    private final UserRepository userRepository;

    private final UserDetailsServiceImpl userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;
    private final ImageService imageService;
    @Override
    public UserDto createUser(CreateUserDto createUserDto) {
        if (userRepository.existsByEmail(user.getEmail())) {

            throw new ValidationException(String.format("Пользователь \"%s\" уже существует!", user.getEmail()));
        }
        User createdUser = userMapper.createUserDtoToEntity(user);
        if (createdUser.getRole() == null) {
            createdUser.setRole(USER);
        }
        return userMapper.toDto(userRepository.save(createdUser));
    }

    @Override
    public Collection<UserDto> getUsers() {
        return userMapper.toDto(userRepository.findAll());
    }

    @Override
    public UserDto getUserMe(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateUser(UserDto user) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        user.setFirstName(updatedUserDto.getFirstName());
        user.setLastName(updatedUserDto.getLastName());
        user.setPhone(updatedUserDto.getPhone());
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found!"));
    }

    @Override
    public void updatePassword(String newPassword, String currentPassword) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            userDetailsService.loadUserByUsername(user.getEmail());
        }
    }
    public String updateUserImage(MultipartFile image, Authentication authentication) throws IOException {
        User user = getUserByUsername(authentication.getName());
        if (user.getImage() != null) {
            imageService.removeImage(user.getImage());
        }
        user.setImage(imageService.uploadImage(image));
        return "/users/image/" + userRepository.save(user).getImage().getId();
    }
    private User getUserByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("The user with email: \"%s\" not found", username)));
    }
    @Override
    public UserDto updateRole(long id, Role role) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден!"));
        user.setRole(role);
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
