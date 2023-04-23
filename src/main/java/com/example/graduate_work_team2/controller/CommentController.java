package com.example.graduate_work_team2.controller;

import com.example.graduate_work_team2.dto.CommentDto;
import com.example.graduate_work_team2.dto.ResponseWrapperComment;
import com.example.graduate_work_team2.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Класс контроллера объекта "Комментарий"
 *
 * @author Одокиенко Екатерина
 */
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
@Tag(name = "Комментарий", description = "CommentController")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "Просмотр комментариев к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Комментарии",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto[].class)
                            )
                    )
            }
    )
    @GetMapping("/{adsId}/comments")
    public ResponseWrapperComment<CommentDto> getAdsComments(@PathVariable long adsId) {
        return ResponseWrapperComment.of(commentService.getComments(adsId));
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
                    )
            }
    )
    @PostMapping("/{adsId}/comments")
    public CommentDto addAdsComments(@PathVariable long adsId, @RequestBody CommentDto commentDto, Authentication authentication) {
        return commentService.addAdsComments(adsId, commentDto, authentication);
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
                    )
            }
    )
    @DeleteMapping("/{adsId}/comments/{comId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable long adsId, @PathVariable long comId,
                                                       Authentication authentication) {
        if (commentService.deleteComment(adsId, comId, authentication)) {
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
                    )
            }
    )
    @PatchMapping("/{adsId}/comments/{comId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable long adsId, @PathVariable long comId,
                                                          @RequestBody CommentDto updateCommentDto,
                                                          Authentication authentication) {
        CommentDto updatedCommentDto = commentService.updateComment(adsId, comId,
                updateCommentDto, authentication);
        if (updateCommentDto.equals(updatedCommentDto)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(updatedCommentDto);
    }
}
