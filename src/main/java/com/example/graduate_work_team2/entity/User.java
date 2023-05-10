package com.example.graduate_work_team2.entity;

import com.example.graduate_work_team2.dto.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

public class User{
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
//    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Ads> ads = new ArrayList<>();
//
//    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
//    private List<Comment> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (id != 0) {
            return id == user.id;
        } else {
            return Objects.equals(firstName, user.firstName)
                    && Objects.equals(lastName, user.lastName)
                    && Objects.equals(email, user.email)
                    && Objects.equals(password, user.password)
                    && Objects.equals(phone, user.phone)
                    && Objects.equals(city, user.city)
                    && Objects.equals(regDate, user.regDate)
                    && Objects.equals(image, user.image)
                    && role == user.role;
        }
    }

    @Override
    public int hashCode() {
        if (id != 0) {
            return Objects.hash(id);
        }
        return Objects.hash(id, firstName, lastName, email, password, phone, city, regDate, image, role);
    }


}
