package com.example.graduate_work_team2.controller;

import com.example.graduate_work_team2.dto.*;
import com.example.graduate_work_team2.service.AdsService;
import com.example.graduate_work_team2.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "Получить все объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все найденные объявления",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperAds.class)
                            )
                    )
            }
    )

    @GetMapping
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        return ResponseEntity.ok(adsService.getAllAdsDto());
    }
//    @SneakyThrows
    @Operation(summary = "Добавить объявление",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Добавленное объявление",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Для доступа к запрашиваемому ресурсу требуется аутентификация", content = @Content())
            }
    )

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAds(@Parameter(in = ParameterIn.DEFAULT, description = "Данные нового объявления",
            required = true, schema = @Schema())
                                         @RequestPart("image") MultipartFile image,
                                         @RequestPart("properties") CreateAdsDto dto) throws IOException {
        return ResponseEntity.ok(adsService.addAds(dto, image));
    }
    @Operation(summary = "Получить информацию об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Вся информация об объявлении",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FullAdsDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Для доступа к запрашиваемому ресурсу требуется аутентификация", content = @Content())
            }
    )

    @GetMapping("/{adsId}")
    public ResponseEntity<FullAdsDto> getFullAdsDto(@PathVariable("adsId") Long adsId) {
        return ResponseEntity.ok(adsService.getFullAdsDto(adsId));
    }
    @Operation(summary = "Удаление объявления",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Были переданы только заголовки без тела сообщения", content = @Content()),
                    @ApiResponse(responseCode = "401", description = "Для доступа к запрашиваемому ресурсу требуется аутентификация", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Доступ к запрошенному ресурсу запрещен", content = @Content())
            }
    )

    @DeleteMapping("/{adsId}")
    public ResponseEntity<HttpStatus> removeAds(@PathVariable Long adsId) {
        if (adsService.removeAdsById(adsId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
    }
    @Operation(summary = "Обновить информацию об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененное объявление",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Для доступа к запрашиваемому ресурсу требуется аутентификация", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Доступ к запрошенному ресурсу запрещен", content = @Content())
            }
    )

    @PatchMapping("/{adsId}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable Long adsId,
                                            @RequestBody CreateAdsDto createAdsDto) {
                return ResponseEntity.ok(adsService.updateAdsDto(adsId,createAdsDto));
    }
    @Operation(summary = "Получить объявления авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все объявления авторизованного пользователя",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperAds.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Для доступа к запрашиваемому ресурсу требуется аутентификация", content = @Content())
            }
    )

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAdsMe() {
        return ResponseEntity.ok(adsService.getAdsMe());
    }
    @Operation(summary = "Обновить картинку объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новое фото",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                    array = @ArraySchema(schema = @Schema(type = "string", format = "byte"))
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Для доступа к запрашиваемому ресурсу требуется аутентификация", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Доступ к запрошенному ресурсу запрещен", content = @Content())
            }
    )

    @PatchMapping(value = "/{adsId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte []> updateAdsImage(@PathVariable Long adsId,
                                                  @Parameter(in = ParameterIn.DEFAULT,
                                                          description = "Загрузите сюда новое изображение",
            schema = @Schema())
    @RequestPart(value = "image") @Valid MultipartFile image){
        imagesService.updateImageAdsDto(adsId, image);
        return ResponseEntity.ok().build();
    }

}
