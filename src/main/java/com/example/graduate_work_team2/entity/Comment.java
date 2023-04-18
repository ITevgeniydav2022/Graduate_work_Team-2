package com.example.graduate_work_team2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
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
    private Instant createdAt;
    /**
     * поле - текст комментария
     */
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    /**
     * поле - автор комментария
     */
    @JoinColumn(name = "author_id")
    private User author;
    /**
     * поле - объект сущности "Объявление"
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pk_ads")
    private Ads ad;
}
