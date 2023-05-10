package com.example.graduate_work_team2.configuration;

import com.example.graduate_work_team2.entity.User;
import com.example.graduate_work_team2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        return new MyUserConfig(user);
    }

    private User getUserByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Пользователь с email: \"%s\" не найден", username)));
    }

}
