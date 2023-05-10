package com.example.graduate_work_team2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

/**
 * Класс сущности "Комментарий"
 *
 * @author Одокиенко Екатерина
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    /**
     * поле - айди комментария
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * поле - время создания комментария
     */
    private LocalDateTime createdAt;
    /**
     * поле - текст комментария
     */
    private String text;
    /**
     * поле - автор комментария
     */
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    /**
     * поле - объект сущности "Объявление"
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pk_ads")
    private Ads ad;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        if (id != 0) {
            return id == comment.id;
        } else {
            return Objects.equals(createdAt, comment.createdAt)
                    && Objects.equals(text, comment.text)
                    && Objects.equals(author, comment.author)
                    && Objects.equals(ad, comment.ad);
        }

    }

    @Override
    public int hashCode() {
        if (id != 0) {
            return Objects.hash(id);
        }
        return Objects.hash(id, createdAt, text, author, ad);
    }
}
