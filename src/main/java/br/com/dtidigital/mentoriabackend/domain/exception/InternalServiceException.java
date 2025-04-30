package br.com.dtidigital.mentoriabackend.domain.exception;

public class InternalServiceException extends RuntimeException {

    public InternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
