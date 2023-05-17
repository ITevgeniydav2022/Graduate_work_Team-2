package com.example.graduate_work_team2.service.impl;

import com.example.graduate_work_team2.dto.RegisterReqDto;
import com.example.graduate_work_team2.dto.Role;
import com.example.graduate_work_team2.entity.User;
import com.example.graduate_work_team2.mapper.UserMapper;
import com.example.graduate_work_team2.repository.UserRepository;
import com.example.graduate_work_team2.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

/**
 * Имплементация сервиса для регистрации пользователя
 *
 * @author Одокиенко Екатерина
 */

@RequiredArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper  userMapper;

    @Override
    public boolean register(RegisterReqDto registerReqDto, Role role) {
        User user = userMapper.fromDto(registerReqDto);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException(String.format("Пользователь \"%s\" уже зарегистрирован!", user.getEmail()));
        }
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

}
