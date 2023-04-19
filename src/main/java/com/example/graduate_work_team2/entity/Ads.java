package com.example.graduate_work_team2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Класс сущности "Объявление"
 *
 * @author Одокиенко Екатерина
 */
@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "ads")
public class Ads {
    /**
     * поле - айди объявления
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * поле - заголовок объявления
     */
    private String title;
    /**
     * поле - описание объявления
     */
    private String description;
    /**
     * поле - цена в объявлении
     */
    private int price;
    /**
     * поле - автор объявления
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;
    /**
     * поле - фото в объявлении
     */
    @OneToOne()
    @JoinColumn()
    private Image image;

}
