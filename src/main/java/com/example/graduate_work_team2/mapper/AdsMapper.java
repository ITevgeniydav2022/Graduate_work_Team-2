package com.example.graduate_work_team2.mapper;

import com.example.graduate_work_team2.dto.AdsDto;
import com.example.graduate_work_team2.dto.CreateAdsDto;
import com.example.graduate_work_team2.dto.FullAdsDto;
import com.example.graduate_work_team2.entity.Ads;

import java.util.Collection;
/**
 * Интерфейс AdsMapper для класса Ads
 *
 * @author Одокиенко Екатерина
 */
public interface AdsMapper {
    AdsDto toDto(Ads entity);
    Ads fromDto(AdsDto dto);
    FullAdsDto toFullAdsDto(Ads entity);
    Ads fromDto(CreateAdsDto dto);
    Collection<AdsDto> toDto(Collection<Ads> adsCollection);
}
