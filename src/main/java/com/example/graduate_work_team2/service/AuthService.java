package com.example.graduate_work_team2.service;

import com.example.graduate_work_team2.dto.RegisterReq;
import com.example.graduate_work_team2.dto.Role;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
}
