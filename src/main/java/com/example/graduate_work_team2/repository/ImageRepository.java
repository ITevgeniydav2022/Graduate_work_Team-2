package com.example.graduate_work_team2.repository;

import com.example.graduate_work_team2.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс ImageRepository для класса "Фото в объявлении"
 * @author Одокиенко Екатерина
 */

/** Механизм для хранения, извлечения, обновления, удаления и поиска объектов. */
@Repository
public interface ImageRepository extends JpaRepository<Image,String> {


}
