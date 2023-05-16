package com.example.graduate_work_team2.controller;

import com.example.graduate_work_team2.dto.CommentDto;
import com.example.graduate_work_team2.dto.ResponseWrapperComment;
import com.example.graduate_work_team2.entity.Comment;
import com.example.graduate_work_team2.service.CommentService;
import com.example.graduate_work_team2.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Класс контроллера объекта "Комментарий"
 *
 * @author Одокиенко Екатерина
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@Tag(name = "Комментарий", description = "CommentController")
public class CommentController {
    private final CommentService commentService;
    private final ImageService imageService;

    @Operation(summary = "Получить комментарии объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Комментарии",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto[].class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Ошибочный ввод имени и/или пароля", content = @Content())
            }
    )

    @GetMapping("ads/{id}/comments")
    public ResponseEntity<ResponseWrapperComment> getAdsComments(@PathVariable Integer id) {
        log.info("Был вызван метод контроллера для получения комментария объявления");
        ResponseWrapperComment responseWrapperComment = commentService.getCommentsDto(id);
        return ResponseEntity.ok().body(responseWrapperComment);
    }

    @Operation(summary = "Добавить комментарий к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Комментарий",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Ошибочный ввод имени и/или пароля", content = @Content())
            }
    )

    @PostMapping("ads/{id}/comments")
    public ResponseEntity<CommentDto> addAdsComments(@PathVariable Integer id, @RequestBody @Valid CommentDto commentDto) {
        log.info("Был вызван метод контроллера для добавления комментария объявления");
        CommentDto newComDto = commentService.addAdsCommentsDto(id, commentDto);
        return ResponseEntity.ok().body(newComDto);
    }

    @Operation(summary = "Удалить комментарий",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный комментарий",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Ошибочный ввод имени и/или пароля", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Доступ к запрошенному ресурсу запрещен", content = @Content())
            }
    )

    @DeleteMapping("ads/{adsId}/comments/{comId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Integer adsId, @PathVariable Integer comId) {
        log.info("Был вызван метод контроллера для удаления комментария объявления");
        if (commentService.deleteCommentDto(adsId, comId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
    }

    @Operation(summary = "Обновить комментарий",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный комментарий",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Ошибочный ввод имени и/или пароля", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Доступ к запрошенному ресурсу запрещен", content = @Content())
            }
    )

    @PatchMapping("ads/{adsId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Integer adsId, @PathVariable Integer commentId,
                                                    @RequestBody CommentDto updateCommentDto,
                                                    Authentication authentication) {
        log.info("Был вызван метод контроллера для изменения комментария объявления");
        CommentDto updatedCommentDto = commentService.updateComment(adsId, commentId,
                updateCommentDto, authentication);
        if (updateCommentDto.equals(updatedCommentDto)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(updatedCommentDto);
    }

    @GetMapping("/comments/{id}/image")
    public ResponseEntity<byte[]> getImageToComment(@PathVariable int id) {
        byte[] i = commentService.findById(id).getAuthor().getImage().getData();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(i);
    }
}
