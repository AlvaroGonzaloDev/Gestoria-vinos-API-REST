package com.example.demo.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class UsuarioExceptionAdvice {
    @ExceptionHandler(UsuarioNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage userNotFoundHandler(UsuarioNotFoundException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(UsuarioAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorMessage userAlreadyExistsHandle(UsuarioAlreadyExistsException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(UsuarioMenorDeEdadException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorMessage userMenorDeEdadHandle(UsuarioMenorDeEdadException ex) {
        return new ErrorMessage(ex.getMessage());
    }

}
