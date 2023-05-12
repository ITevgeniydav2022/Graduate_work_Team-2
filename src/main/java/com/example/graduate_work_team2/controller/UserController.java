package com.example.graduate_work_team2.controller;

import com.example.graduate_work_team2.dto.NewPasswordDto;
import com.example.graduate_work_team2.dto.UserDto;
import com.example.graduate_work_team2.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * Класс контроллера объекта "Пользователь"
 *
 * @author Одокиенко Екатерина
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Пользователи", description = "UserController")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Обновление пароля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новый пароль",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = NewPasswordDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401",description = "Ошибочный ввод имени и/или пароля", content = @Content()),
                    @ApiResponse(responseCode = "403",description = "Доступ к запрошенному ресурсу запрещен", content = @Content())
            }
    )

    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@Valid @RequestBody NewPasswordDto newPasswordDto) {
        log.info("Был вызван метод контроллера для обновления пароля");
        userService.updatePassword(newPasswordDto.getNewPassword(), newPasswordDto.getCurrentPassword());
        return ResponseEntity.ok(newPasswordDto);
    }

    @Operation(summary = "Получить информацию об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация об авторизованном пользователе",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Для доступа к запрашиваемому ресурсу требуется аутентификация", content = @Content())
            }
    )

    @GetMapping("/users/me")
    public ResponseEntity<UserDto> getUser() {
        log.info("Был вызван метод контроллера для получения информации об авторизованном пользователе");
        return ResponseEntity.ok(userService.getUserDto());
    }

    @Operation(summary = "Обновить информацию об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация об авторизованном пользователе",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Для доступа к запрашиваемому ресурсу требуется аутентификация", content = @Content())
            }
    )
    @PatchMapping("/users/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        log.info("Был вызван метод контроллера для обновления информации об авторизованном пользователе");
        return ResponseEntity.ok(userService.updateUserDto(userDto));
    }

    @Operation(summary = "Обновить аватар авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Аватар авторизованного пользователя обновлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Для доступа к запрашиваемому ресурсу требуется аутентификация", content = @Content())
            }
    )

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte []> updateUserImage(@RequestBody MultipartFile image) throws IOException {
        log.info("Был вызван метод контроллера для обновления аватара авторизованного пользователя");
        userService.updateUserImage(image);
        return ResponseEntity.ok().build();
    }

}