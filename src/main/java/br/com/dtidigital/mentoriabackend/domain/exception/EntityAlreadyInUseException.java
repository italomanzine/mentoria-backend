package br.com.dtidigital.mentoriabackend.domain.exception;


public class EntityAlreadyInUseException extends BusinessException {

    public EntityAlreadyInUseException(String message) {
        super(message);
    }
}