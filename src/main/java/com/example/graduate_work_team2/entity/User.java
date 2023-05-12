package com.example.graduate_work_team2.entity;

import com.example.graduate_work_team2.dto.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Класс сущности "Пользователь"
 *
 * @author Одокиенко Екатерина
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")

public class User implements UserDetails {
    /**
     * поле - айди пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;
    @Column(name = "user_name", nullable = false)
    @Size(min=2, message = "Не меньше 5 знаков")
    private String username;
    /**
     * поле - имя пользователя
     */
    @Column(name = "first_name")
    private String firstName;
    /**
     * поле - фамилия пользователя
     */
    @Column(name = "last_name")
    private String lastName;
    /**
     * поле - email пользователя
     */
    @Column(nullable = false)
    private String email;
    /**
     * поле - пароль пользователя
     */
    @Column( nullable = false)
    @Size(min=2, message = "Не меньше 5 знаков")
    private String password;
    /**
     * поле - номер телефона пользователя
     */
    @Column(nullable = false)
    private String phone;

    /**
     * поле - объект сущности "Фото"
     */
    @OneToOne()
    @JoinColumn(name = "image_id")
    private Image image;
    /**
     * поле - тип пользователя - его роль
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (id != 0) {
            return id == user.id;
        } else {
            return Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName)
                    && Objects.equals(lastName, user.lastName) && Objects.equals(phone, user.phone)
                    && Objects.equals(image, user.image) && Objects.equals(password, user.password)
                    && Objects.equals(username, user.username) && role == user.role;
        }
    }

    @Override
    public int hashCode() {
        if (id != 0) {
            return Objects.hash(id);
        }
        return Objects.hash(email, firstName, lastName, phone, image, password, username, role);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = new HashSet<>();
        roles.add(this.role);
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
