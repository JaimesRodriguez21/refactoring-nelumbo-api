package com.nelumbo.api.exception;

public class EmailDuplicadoException extends  RuntimeException{
    public EmailDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
