package com.example.graduate_work_team2.mapper;

import com.example.graduate_work_team2.dto.AdsDto;
import com.example.graduate_work_team2.entity.Ads;
import com.example.graduate_work_team2.entity.Image;
import com.example.graduate_work_team2.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdsMapper {

    AdsMapper INSTANCE = Mappers.getMapper(AdsMapper.class);

    AdsDto toAdsDto(Ads adsEntity);

    @InheritInverseConfiguration
    Ads fromAdsDto(AdsDto adsDto, User user, Image image);
}
