package com.example.graduate_work_team2.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс сущности "Объявление"
 *
 * @author Одокиенко Екатерина
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Data
@Table(name = "ads")
public class Ads {
    /**
     * поле - айди объявления
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;
    /**
     * поле - заголовок объявления
     */
    @Column(length = 30, nullable = false)
    private String title;
    /**
     * поле - описание объявления
     */
    @Column(length = 500)
    private String description;
    /**
     * поле - цена в объявлении
     */
    @Column(nullable = false)
    private int price;
    /**
     * поле - автор объявления
     */
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    /**
     * поле - фото в объявлении
     */
    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ads ads = (Ads) o;
        if (id != 0) {
            return id == ads.id;
        } else {
            return price == ads.price && Objects.equals(title, ads.title)
                    && Objects.equals(description, ads.description)
                    && Objects.equals(author, ads.author)
                    && Objects.equals(image, ads.image);
        }
    }

    @Override
    public int hashCode() {
        if (id != 0) {
            return Objects.hash(id);
        }
        return Objects.hash(id, title, description, price, author, image);
    }
}