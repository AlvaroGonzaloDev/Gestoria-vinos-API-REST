package com.example.demo.exceptions;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(Long id) {
        super("El usuario de id:" + id + " no encontrado.");
    }
}
