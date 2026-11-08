package com.example.demo.exceptions;

public class UsuarioMenorDeEdadException extends RuntimeException {
    public UsuarioMenorDeEdadException(int edad) {
        super("El usuario de edad:" + edad + " es menor de edad por lo que no puede verificarse.");
    }
}
