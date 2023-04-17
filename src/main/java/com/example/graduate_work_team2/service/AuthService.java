package com.example.graduate_work_team2.service;

import com.example.graduate_work_team2.dto.RegisterReqDto;
import com.example.graduate_work_team2.dto.Role;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReqDto registerReq, Role role);
}
