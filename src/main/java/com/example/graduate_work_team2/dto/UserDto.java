package com.example.graduate_work_team2.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    @JsonIgnore
    private String regDate = String.valueOf(LocalDateTime.now());
    private String image;

}

