package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.ErrorDto;
import com.danikvitek.MCPluginMarketplace.util.exception.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public final class HandlerController {
    private final MessageSource messageSource;

    @ExceptionHandler({
            PluginNotFoundException.class,
            UserNotFoundException.class,
            CategoryNotFoundException.class,
            TagNotFoundException.class,
            CommentNotFoundException.class,
            PluginRatingNotFoundException.class
    })
    public @NotNull ResponseEntity<ErrorDto> handleNotFoundException(@NotNull RuntimeException e,
                                                                     WebRequest request) {
        ErrorDto error = ErrorDto.builder()
                .code("404 NOT FOUND")
                .description(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler({
            PluginAlreadyExistsException.class,
            CategoryAlreadyExistsException.class,
            TagAlreadyExistsException.class,
            AuthorsSetIsEmptyException.class,
            IllegalArgumentException.class
    })
    public @NotNull ResponseEntity<ErrorDto> handleBadRequestException(@NotNull RuntimeException e,
                                                                       WebRequest request) {
        ErrorDto error = ErrorDto.builder()
                .code("400 BAD REQUEST")
                .description(e.getMessage())
                .build();
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public @NotNull ResponseEntity<Set<ErrorDto>> handleValidationException(@NotNull ConstraintViolationException e,
                                                                            WebRequest request) {
        Set<ErrorDto> errors = e.getConstraintViolations().stream()
                .map(violation -> ErrorDto.builder()
                        .code("400 BAD REQUEST")
                        .description(violation.getPropertyPath() + " invalid. " +
                                messageSource.getMessage(violation.getMessage(), null, request.getLocale()))
                        .build())
                .collect(Collectors.toSet());
        return ResponseEntity.badRequest().body(errors);
    }
}
