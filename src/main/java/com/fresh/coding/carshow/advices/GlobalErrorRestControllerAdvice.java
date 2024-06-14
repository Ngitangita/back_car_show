package com.fresh.coding.carshow.advices;

import com.fresh.coding.carshow.exceptions.BadRequestException;
import com.fresh.coding.carshow.exceptions.InternalServerException;
import com.fresh.coding.carshow.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalErrorRestControllerAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ApplicationError<String> handleError(AccessDeniedException ex) {
        return new ApplicationError<>(
                ex.getMessage(),
                LocalDate.now(),
                HttpStatus.FORBIDDEN.value()
        );
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApplicationError<String> handleError(NotFoundException ex) {
        return new ApplicationError<>(
                ex.getMessage(),
                LocalDate.now(),
                HttpStatus.NOT_FOUND.value()
        );
    }

    @ExceptionHandler({BadRequestException.class, IllegalArgumentException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationError<String> handleError(BadRequestException ex) {
        return new ApplicationError<>(
                ex.getMessage(),
                LocalDate.now(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationError<Map<String, String>> handleError(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ApplicationError<>(
                errors,
                LocalDate.now(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApplicationError<String> handlerError(InternalServerException ex) {
        return new ApplicationError<>(
                ex.getMessage(),
                LocalDate.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApplicationError<String> handleError(UsernameNotFoundException ex) {
        return new ApplicationError<>(
                ex.getMessage(),
                LocalDate.now(),
                HttpStatus.UNAUTHORIZED.value()
        );
    }
}
