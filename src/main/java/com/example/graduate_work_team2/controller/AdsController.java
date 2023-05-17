package com.example.graduate_work_team2.controller;

import com.example.graduate_work_team2.dto.AdsDto;
import com.example.graduate_work_team2.dto.CreateAdsDto;
import com.example.graduate_work_team2.dto.FullAdsDto;
import com.example.graduate_work_team2.dto.ResponseWrapperAds;
import com.example.graduate_work_team2.service.AdsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * Класс контроллера объекта "Объявление"
 *
 * @author Одокиенко Екатерина
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "AdsController")
public class AdsController {
    private final AdsService adsService;

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
        log.info("Был вызван метод контроллера для получения всех объявлений");
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
    public ResponseEntity<AdsDto> addAds(@RequestPart(name = "image")
                                         MultipartFile image,
                                         @RequestPart(name = "properties")
                                         CreateAdsDto properties) throws IOException {
        log.info("Был вызван метод контроллера для добавления объявления");
        return ResponseEntity.ok(adsService.addAds(properties, image));
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

    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getFullAdsDto(@PathVariable("id") Integer id) {
        log.info("Был вызван метод контроллера для получения всей информации об объявлении");
        return ResponseEntity.ok(adsService.getFullAdsDto(id));
    }
    @Operation(summary = "Удаление объявления",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Были переданы только заголовки без тела сообщения", content = @Content()),
                    @ApiResponse(responseCode = "401", description = "Для доступа к запрашиваемому ресурсу требуется аутентификация", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Доступ к запрошенному ресурсу запрещен", content = @Content())
            }
    )

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeAds(@PathVariable Integer id) {
        log.info("Был вызван метод контроллера для удаления объявления");
        if (adsService.removeAdsById(id)) {
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

    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable Integer id,
                                            @RequestBody CreateAdsDto createAdsDto) {
        log.info("Был вызван метод контроллера для изменения объявления");
        return ResponseEntity.ok(adsService.updateAdsDto(id,createAdsDto));
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
        log.info("Был вызван метод контроллера для получения объявления авторизованного пользователя");
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

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte []> updateAdsImage(@PathVariable Integer id,
                                                  @Parameter(in = ParameterIn.DEFAULT,
                                                          description = "Загрузите сюда новое изображение",
                                                          schema = @Schema())
                                                  @RequestPart(value = "image") @Valid MultipartFile image) throws IOException {
        log.info("Был вызван метод контроллера для обновления картинки объявления");
        adsService.updateAdsImage(id,image);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImageFromAuthUser(@PathVariable int id) {
        byte[] i = adsService.findById(id).getImage().getData();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(i);
    }

}

