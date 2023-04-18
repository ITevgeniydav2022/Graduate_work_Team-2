package com.example.graduate_work_team2.service.impl;

import com.example.graduate_work_team2.entity.User;
import com.example.graduate_work_team2.repository.UserRepository;
import com.example.graduate_work_team2.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
/**
 * Имплементация сервиса для регистрации пользователя
 *
 * @author Одокиенко Екатерина
 */
@Transactional
@RequiredArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException(String.format("Пользователь \"%s\" уже зарегистрирован!", user.getEmail()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
