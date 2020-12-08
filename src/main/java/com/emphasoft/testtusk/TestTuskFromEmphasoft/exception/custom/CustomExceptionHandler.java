

package com.emphasoft.testtusk.TestTuskFromEmphasoft.exception.custom;

import com.emphasoft.testtusk.TestTuskFromEmphasoft.exception.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class CustomExceptionHandler {


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorizedException.class})
    protected String handleUnauthorizedException(final UnauthorizedException ex, Model model) {
        log.warn(ex.getMessage());
        model.addAttribute("message", "Для доступа к этому разделу нужно авторизоваться");
        return "error";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({PermissionException.class})
    protected String handleForbiddenException(final PermissionException ex, Model model) {
        log.warn(ex.getMessage());
        model.addAttribute("message", "Доступ к этому разделу есть только у админестратора");
        return "error";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    protected String handleBadRequestException(final BadRequestException ex, Model model) {
        log.warn(ex.getMessage());
        model.addAttribute("message", "Запрос составлен некорректно");
        return "error";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    protected String handleNotFoundException(final ResourceNotFoundException ex, Model model) {
        log.warn(ex.getMessage());
        model.addAttribute("message", "Объект не найден");
        return "error";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NotUniqueValueException.class})
    protected String handleNotUniqueValueException(final NotUniqueValueException ex, Model model) {
        log.warn(ex.getMessage());
        model.addAttribute("message", "Имя пользователя и Email должны быть уникальны");
        return "error";
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler({JsonProcessingException.class})
    protected String handleJsonParseException(final JsonProcessingException ex, Model model) {
        log.error(ex.getMessage());
        model.addAttribute("message", "Не удаось получить информацию о курсе валют");
        return "error";
    }
}
