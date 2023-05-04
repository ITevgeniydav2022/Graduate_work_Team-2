package com.example.graduate_work_team2.entity;

import com.example.graduate_work_team2.dto.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Класс сущности "Пользователь"
 *
 * @author Одокиенко Екатерина
 */
@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "users")

public class User {
    /**
     * поле - айди пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * поле - имя пользователя
     */
    private String firstName;
    /**
     * поле - фамилия пользователя
     */
    private String lastName;
    /**
     * поле - email пользователя
     */
    private String email;
    /**
     * поле - пароль пользователя
     */
    private String password;
    /**
     * поле - номер телефона пользователя
     */
    private String phone;
    /**
     * поле - город проживания пользователя
     */
    private String city;
    /**
     * поле - дата регистрации пользователя
     */
    private LocalDateTime regDate;
    /**
     * поле - объект сущности "Фото"
     */
    @OneToOne()
    private Image image;
    /**
     * поле - тип пользователя - его роль
     */
    @Enumerated(EnumType.STRING)
    private Role role;

}
