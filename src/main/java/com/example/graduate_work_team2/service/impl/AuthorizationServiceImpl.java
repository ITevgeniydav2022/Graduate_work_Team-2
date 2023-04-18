package com.example.graduate_work_team2.service.impl;

import com.example.graduate_work_team2.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
/**
 * Имплементация сервиса для авторизации пользователя
 *
 * @author Одокиенко Екатерина
 */
@Transactional
@RequiredArgsConstructor
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    @Override
    public boolean login(String username, String password) {
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Неверно указан пароль!");
        }
        return false;
    }

}

