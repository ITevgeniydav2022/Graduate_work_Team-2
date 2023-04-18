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
                    )
            }
    )
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@Valid @RequestBody NewPasswordDto newPasswordDto) {
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
                    )
            }
    )
    @GetMapping("/users/me")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserMe(authentication));
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
                    )
            }
    )
    @PatchMapping("/users/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @Operation(summary = "Обновить аватар авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Аватар авторизованного пользователя",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    )
            }
    )
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserImage(@RequestBody MultipartFile image, Authentication authentication) throws IOException {
        return ResponseEntity.ok().body(userService.updateUserImage(image, authentication));
    }

}