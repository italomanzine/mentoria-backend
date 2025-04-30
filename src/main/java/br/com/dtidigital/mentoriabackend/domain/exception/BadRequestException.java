package br.com.dtidigital.mentoriabackend.domain.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BadRequestException extends BusinessException {

    public BadRequestException(String message) {
        super(message);
    }

}