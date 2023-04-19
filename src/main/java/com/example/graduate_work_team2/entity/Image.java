package com.example.graduate_work_team2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * поле - размер фото
     */
    private long fileSize;
    /**
     * поле - путь к фото
     */
    private String filePath;
    /**
     * поле - тип фото
     */
    private String mediaType;
    /**
     * поле - тип дата-данных фото
     */
    @Lob
    @Type(type = "binary")
    private byte[] data;
    /**
     * поле - объект сущности "Объявление"
     */
    @OneToOne
    private Ads ads;
    /**
     * поле - строкое представление сущности "Фото"
     */
    public String toString() {
        return "Объявление: id = " + this.getId() + ", инфо = " + Arrays.toString((this.getData()));
    }

}
