package com.example.graduate_work_team2.controller;

import com.example.graduate_work_team2.service.AuthorizationService;
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
import com.example.graduate_work_team2.dto.LoginReqDto;

/**
 * Класс контроллера для авторизации пользователя
 *
 * @author Одокиенко Екатерина
 */
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Tag(name = "Авторизация", description = "AuthorizationController")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @Operation(summary = "Авторизация пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Авторизированный пользователь",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LoginReqDto.class)
                            )
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDto req) {
        if (authorizationService.login(req.getUsername(), req.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
