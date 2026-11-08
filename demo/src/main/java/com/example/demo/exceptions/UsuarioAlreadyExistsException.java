package com.example.demo.exceptions;

public class UsuarioAlreadyExistsException extends RuntimeException {
    public UsuarioAlreadyExistsException(Long id) {
        super("El usuario de id:" + id + " ya existe.");
    }
}
