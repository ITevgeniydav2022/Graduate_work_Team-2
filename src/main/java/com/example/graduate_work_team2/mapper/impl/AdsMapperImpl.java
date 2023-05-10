package com.example.graduate_work_team2.mapper.impl;

import com.example.graduate_work_team2.dto.AdsDto;
import com.example.graduate_work_team2.dto.CreateAdsDto;
import com.example.graduate_work_team2.dto.FullAdsDto;
import com.example.graduate_work_team2.entity.Ads;
import com.example.graduate_work_team2.mapper.AdsMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class AdsMapperImpl implements AdsMapper {
    @Override
    public AdsDto toDto(Ads entity) {
        AdsDto adsDto = new AdsDto();
        adsDto.setPk(Math.toIntExact(entity.getId()));
        adsDto.setAuthor(Math.toIntExact(entity.getAuthor().getId()));
        adsDto.setPrice(entity.getPrice());
        adsDto.setImage("/ads/" + entity.getImage().getId() + "/image");
        adsDto.setTitle(entity.getTitle());
        return adsDto;
    }

    @Override
    public Ads fromDto(AdsDto dto) {
        Ads mappedAds = new Ads();
        mappedAds.setId(dto.getPk());
        mappedAds.getAuthor().setId(dto.getAuthor());
        mappedAds.setPrice(dto.getPrice());
        mappedAds.getImage().setId(Long.parseLong(dto.getImage()));
        mappedAds.setTitle(dto.getTitle());
        return mappedAds;
    }

    @Override
    public FullAdsDto toFullAdsDto(Ads entity) {
        FullAdsDto fullAdsDto = new FullAdsDto();
        fullAdsDto.setPk(Math.toIntExact(entity.getId()));
        fullAdsDto.setAuthorFirstName(entity.getAuthor().getFirstName());
        fullAdsDto.setAuthorLastName(entity.getAuthor().getLastName());
        fullAdsDto.setEmail(entity.getAuthor().getEmail());
        fullAdsDto.setPhone(entity.getAuthor().getPhone());
        fullAdsDto.setTitle(entity.getTitle());
        fullAdsDto.setDescription(entity.getDescription());
        fullAdsDto.setImage("/ads/" + entity.getImage().getId() + "/image");
        fullAdsDto.setPrice(entity.getPrice());
        return fullAdsDto;
    }

    @Override
    public Ads fromDto(CreateAdsDto dto) {
        Ads ads = new Ads();
        ads.setTitle(dto.getTitle());
        ads.setDescription(dto.getDescription());
        ads.setPrice(dto.getPrice());
        return ads;
    }

    @Override
    public Collection<AdsDto> toDto(Collection<Ads> adsCollection) {
        List<AdsDto> dtoList = new ArrayList<>(adsCollection.size());
        for (Ads ads : adsCollection) {
            dtoList.add(toDto(ads));
        }
        return dtoList;
    }
}
