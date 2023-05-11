package com.example.graduate_work_team2.repository;

import com.example.graduate_work_team2.entity.Ads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




/**
 * Интерфейс AdsRepository для класса "Объявление"
 * @author Одокиенко Екатерина
 */

/** Механизм для хранения, извлечения, обновления, удаления и поиска объектов. */
@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {

}
