package com.example.graduate_work_team2.mapper.impl;

import com.example.graduate_work_team2.dto.AdsDto;
import com.example.graduate_work_team2.dto.CreateAdsDto;
import com.example.graduate_work_team2.dto.FullAdsDto;
import com.example.graduate_work_team2.entity.Ads;
import com.example.graduate_work_team2.mapper.AdsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Slf4j
@Component
public class AdsMapperImpl implements AdsMapper {
    @Override
    public AdsDto toDto(Ads entity) {
        log.info("Был вызван метод маппера из Ads entity в AdsDto");
        AdsDto adsDto = new AdsDto();
        adsDto.setPk(entity.getId());
        adsDto.setAuthor(entity.getAuthor().getId());
        adsDto.setPrice(entity.getPrice());
        adsDto.setImage("/ads/" + entity.getId() + "/image");
        adsDto.setTitle(entity.getTitle());
        return adsDto;
    }

    @Override
    public Ads fromDto(AdsDto dto) {
        log.info("Был вызван метод маппера из AdsDto в Ads entity");
        Ads mappedAds = new Ads();
        mappedAds.setId(dto.getPk());
        mappedAds.getAuthor().setId(dto.getAuthor());
        mappedAds.setPrice(dto.getPrice());
        mappedAds.getImage().setId(dto.getImage());
        mappedAds.setTitle(dto.getTitle());
        return mappedAds;
    }

    @Override
    public FullAdsDto toFullAdsDto(Ads entity) {
        log.info("Был вызван метод маппера из Ads entity в FullAdsDto");
        FullAdsDto fullAdsDto = new FullAdsDto();
        fullAdsDto.setPk(entity.getId());
        fullAdsDto.setAuthorFirstName(entity.getAuthor().getFirstName());
        fullAdsDto.setAuthorLastName(entity.getAuthor().getLastName());
        fullAdsDto.setEmail(entity.getAuthor().getEmail());
        fullAdsDto.setPhone(entity.getAuthor().getPhone());
        fullAdsDto.setTitle(entity.getTitle());
        fullAdsDto.setDescription(entity.getDescription());
        fullAdsDto.setImage("/ads/" + entity.getId() + "/image");
        fullAdsDto.setPrice(entity.getPrice());
        return fullAdsDto;
    }

    @Override
    public Ads fromDto(CreateAdsDto dto) {
        log.info("Был вызван метод маппера из CreateAdsDto dto в Ads entity");
        Ads ads = new Ads();
        ads.setTitle(dto.getTitle());
        ads.setDescription(dto.getDescription());
        ads.setPrice(dto.getPrice());
        return ads;
    }

    @Override
    public Collection<AdsDto> toDto(Collection<Ads> adsCollection) {
        log.info("Был вызван метод маппера из adsCollection dto в Collection<AdsDto>");
        List<AdsDto> dtoList = new ArrayList<>(adsCollection.size());
        for (Ads ads : adsCollection) {
            dtoList.add(toDto(ads));
        }
        return dtoList;
    }
}
