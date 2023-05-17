package com.example.graduate_work_team2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * Класс сущности "Фото"
 *
 * @author Одокиенко Екатерина
 */
@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "image")
public class Image {
    /**
     * поле - айди фото
     */
    @Id
    private String id;

    /**
     * поле - путь к фото
     */
    private String filePath;

    /**
     * поле - тип дата-данных фото
     */

    @Column(name = "page")
    private byte[] data;
    /**
     * поле - объект сущности "Объявление"
     */
    @OneToOne
    private Ads ads;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id)
                && Objects.equals(filePath, image.filePath)
                && Arrays.equals(data, image.data)
                && Objects.equals(ads, image.ads);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, filePath, ads);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
