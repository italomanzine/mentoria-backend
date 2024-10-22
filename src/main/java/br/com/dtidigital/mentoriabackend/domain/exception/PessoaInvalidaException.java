package br.com.dtidigital.mentoriabackend.domain.exception;

public class PessoaInvalidaException extends RuntimeException {
    public PessoaInvalidaException(String message) {
        super(message);
    }
}
