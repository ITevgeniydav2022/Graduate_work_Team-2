package com.example.graduate_work_team2.service.impl;

import com.example.graduate_work_team2.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * Имплементация сервиса для авторизации пользователя
 *
 * @author Одокиенко Екатерина
 */
@Slf4j
//@Transactional
@RequiredArgsConstructor
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userService;


    @Override
    public boolean login(String username, String password) {
        log.info("Был вызван метод введения пароля");
        UserDetails user = userService.loadUserByUsername(username);
        return passwordEncoder.matches(password, user.getPassword());
//
//
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new BadCredentialsException("Неверно указан пароль!");
//        }

    }

}
