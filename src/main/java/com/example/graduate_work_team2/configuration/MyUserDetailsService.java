package com.example.graduate_work_team2.configuration;

import com.example.graduate_work_team2.entity.User;
import com.example.graduate_work_team2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow();
    }

//    private User getUserByUsername(String email) {
//        return userRepository.findByEmail(email).orElseThrow(() ->
//                new UsernameNotFoundException(String.format("Пользователь с email: \"%s\" не найден", email)));
//    }
}
