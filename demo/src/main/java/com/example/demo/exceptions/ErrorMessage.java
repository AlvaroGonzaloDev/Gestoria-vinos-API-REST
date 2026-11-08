package com.example.demo.exceptions;

public class ErrorMessage {
    private String mensajeDeError;

    public ErrorMessage() {

    }

    public ErrorMessage(String mensajeDeError) {
        this.mensajeDeError = mensajeDeError;
    }

    public String getMensajeDeError() {
        return mensajeDeError;
    }

    public void setMensajeDeError(String mensajeDeError) {
        this.mensajeDeError = mensajeDeError;
    }

}
