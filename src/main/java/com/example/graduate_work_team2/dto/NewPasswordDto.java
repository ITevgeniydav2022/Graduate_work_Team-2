package com.example.graduate_work_team2.dto;

import lombok.Data;

@Data
public class NewPasswordDto {

    /**текущий пароль**/
    private String currentPassword;

    /**новый пароль**/
    private String newPassword;

}
