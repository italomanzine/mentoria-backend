package br.com.dtidigital.mentoriabackend.domain.service.pessoa.impl;

import br.com.dtidigital.mentoriabackend.api.v1.model.input.PessoaInput;
import br.com.dtidigital.mentoriabackend.domain.exception.PessoaInvalidaException;
import br.com.dtidigital.mentoriabackend.domain.service.ValidarPessoaService;

public class ValidarStackPessoaServiceImpl implements ValidarPessoaService {
    @Override
    public void validar(PessoaInput pessoa) {
        if (pessoa.getStack() != null) {
            for (String stackItem : pessoa.getStack()) {
                if (stackItem == null || stackItem.trim().isEmpty()) {
                    throw new PessoaInvalidaException("Cada item de stack deve ser uma string não vazia");
                }
                if (stackItem.length() > 32) {
                    throw new PessoaInvalidaException("Cada item de stack deve ter no máximo 32 caracteres");
                }
            }
        }
    }
}
