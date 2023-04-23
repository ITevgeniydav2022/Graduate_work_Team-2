package com.example.graduate_work_team2.mapper;

import com.example.graduate_work_team2.dto.AdsDto;
import com.example.graduate_work_team2.dto.CreateAdsDto;
import com.example.graduate_work_team2.dto.FullAdsDto;
import com.example.graduate_work_team2.entity.Ads;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper
public interface AdsMapper {

//    @Mapping(target = "author", source = "author.id") - в дальнейшем уточнить!
    @Mapping(source = "id", target = "pk")
    @Mapping(target = "image", expression = "java(\"/ads/images/\" + entity.getImage().getId())")
    AdsDto toAdsDto(Ads entity);


//    @Mapping(target = "author", source = "author.id")-в дальнейшем уточнить!
    @Mapping(target = "image", ignore = true)
    @Mapping(source = "pk", target = "id")
    Ads fromAdsDto(AdsDto dto);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(source = "pk", target = "id")
    Ads fromAdsDto(CreateAdsDto dto);

    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
//    @Mapping(target = "phone", source = "author.phone") - в дальнейшем уточнить!
//    @Mapping(target = "email", source = "author.email") - аналогично
    @Mapping(target = "image", expression = "java(\"/ads/images/\" + entity.getImage().getId())")
    FullAdsDto toFullAdsDto(Ads entity);
    List<AdsDto> toAdsDto(Collection<Ads> entity);

//    List<Ads> fromAdsDto(Collection<AdsDto> dto); - аналгично

}
