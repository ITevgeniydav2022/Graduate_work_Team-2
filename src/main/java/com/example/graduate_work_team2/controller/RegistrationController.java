package com.example.graduate_work_team2.controller;

import com.example.graduate_work_team2.dto.RegisterReqDto;
import com.example.graduate_work_team2.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Класс контроллера для регистрации пользователя
 *
 * @author Одокиенко Екатерина
 */
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Tag(name = "Регистрация", description = "RegistrationController")
public class RegistrationController {

    private final RegistrationService registrationService;
    @Operation(summary = "Регистрация пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Зарегистрированный пользователь",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RegisterReqDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400",
                            description = "Пользователь не найден!")
            }
    )
    /**Метод регистрации пользователя**/
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterReqDto req) {
        if (registrationService.register(req)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
