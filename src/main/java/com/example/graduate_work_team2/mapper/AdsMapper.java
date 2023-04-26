package com.example.graduate_work_team2.mapper;

import com.example.graduate_work_team2.dto.AdsDto;
import com.example.graduate_work_team2.dto.CreateAdsDto;
import com.example.graduate_work_team2.dto.FullAdsDto;
import com.example.graduate_work_team2.entity.Ads;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",uses = {Ads.class})
public interface AdsMapper {
//    String ADS_IMAGES = "/ads/image/";

    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "pk",source = "id")
    @Mapping(target = "image", expression = "java(\"/ads/images/\" + entity.getImage().getId())")
    AdsDto toAdsDto(Ads entity);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "id", ignore = true)
    Ads fromAdsDto(CreateAdsDto dto);

    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "image", expression = "java(\"/ads/images/\" + entity.getImage().getId())")
    @Mapping(target = "pk",source = "id")
    FullAdsDto toFullAdsDto(Ads entity);
    List<AdsDto> toAdsDto(Collection<Ads> entity);

//    @Mapping(target = "author", source = "author.id")
//    @Mapping(target = "image", ignore = true)
//    @Mapping(source = "pk", target = "id")
//    Ads fromAdsDto(AdsDto dto);

}
