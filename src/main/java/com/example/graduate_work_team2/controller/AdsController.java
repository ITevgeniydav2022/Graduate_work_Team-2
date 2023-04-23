package com.example.graduate_work_team2.controller;

import com.example.graduate_work_team2.dto.AdsDto;
import com.example.graduate_work_team2.dto.CreateAdsDto;
import com.example.graduate_work_team2.dto.FullAdsDto;
import com.example.graduate_work_team2.dto.ResponseWrapperAds;
import com.example.graduate_work_team2.entity.Ads;
import com.example.graduate_work_team2.mapper.AdsMapper;
import com.example.graduate_work_team2.service.AdsService;
import com.example.graduate_work_team2.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * Класс контроллера объекта "Объявление"
 *
 * @author Одокиенко Екатерина
 */
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "AdsController")
public class AdsController {
    private final AdsService adsService;

    private final ImageService imagesService;
    private final AdsMapper adsMapper;

    @Operation(summary = "Получить все объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все найденные объявления",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto[].class)
                            )
                    )
            }
    )
    @GetMapping
    public ResponseWrapperAds<AdsDto> getAllAds() {
        return ResponseWrapperAds.of(adsService.getAllAds());
    }
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @Operation(summary = "Добавление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавленное объявление",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class)
                            )
                    )
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAds(@Parameter(in = ParameterIn.DEFAULT, description = "Данные нового объявления",
            required = true, schema = @Schema())
                                         @RequestPart("image") MultipartFile image,
                                         @RequestPart("properties") @Valid CreateAdsDto dto) throws IOException {
        return ResponseEntity.ok(adsService.addAds(dto, image));
    }
    @Operation(summary = "Получить информацию об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Вся информация об объявлении",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto[].class)
                            )
                    )
            }
    )
    @GetMapping("/{adsId}")
    public ResponseEntity<FullAdsDto> getFullAdsDto(@PathVariable("adsId") Long adsId) {
        return ResponseEntity.ok(adsMapper.toFullAdsDto(adsService.getAdsById(adsId)));
    }
    @Operation(summary = "Удаление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленное объявление",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)
                            )
                    )
            }
    )
    @DeleteMapping("/{adsId}")
    public ResponseEntity<HttpStatus> removeAds(@PathVariable long adsId, Authentication authentication) throws IOException {
        if (adsService.removeAdsById(adsId, authentication)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
    }
    @Operation(summary = "Изменение объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененное объявление",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class)
                            )
                    )
            }
    )
    @PatchMapping("/{adsId}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable long adsId,
                                            @RequestBody AdsDto updatedAdsDto,Authentication authentication) {

        AdsDto updateAdsDto = adsService.updateAds(adsId, updatedAdsDto, authentication);
        if (updateAdsDto.equals(updatedAdsDto)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(updateAdsDto);
    }
    @Operation(summary = "Просмотр всех объявлений авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все объявления авторизованного пользователя",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto[].class)
                            )
                    )
            }
    )
    @GetMapping("/me")
    public ResponseWrapperAds<AdsDto> getAdsMe(Authentication authentication) {
        return ResponseWrapperAds.of(adsService.getAdsMe(authentication));
    }
    @Operation(summary = "Редактирование фото в объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новое фото",
                            content = @Content(
                                    mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                    schema = @Schema(implementation = AdsDto.class)
                            )
                    )
            }
    )
    @PatchMapping(value = "/{adsId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> updateAdsImage(@PathVariable long adsId, Authentication authentication,
                                                 @Parameter(in = ParameterIn.DEFAULT, description = "Загрузите сюда новое изображение",
            schema = @Schema())
    @RequestPart(value = "image") @Valid MultipartFile image) throws IOException {
        return ResponseEntity.ok(imagesService.updateImage(image, authentication, adsId));
    }

}
