package com.example.graduate_work_team2.configuration;

import com.example.graduate_work_team2.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class MyUserConfig extends org.springframework.security.core.userdetails.User {
    private final long id;

    public MyUserConfig(User user) {
        super(user.getEmail(), user.getPassword(), List.of(user.getRole()));
        this.id = user.getId();
    }
    @Override
    public void eraseCredentials() {
    }
}
