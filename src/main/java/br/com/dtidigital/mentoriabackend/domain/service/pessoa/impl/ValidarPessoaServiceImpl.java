package br.com.dtidigital.mentoriabackend.domain.service.pessoa.impl;

import br.com.dtidigital.mentoriabackend.api.v1.model.input.PessoaInput;
import br.com.dtidigital.mentoriabackend.domain.exception.PessoaInvalidaException;
import br.com.dtidigital.mentoriabackend.domain.service.ValidarPessoaService;

public class ValidarPessoaServiceImpl implements ValidarPessoaService {
    @Override
    public void validar(PessoaInput pessoa) {
        if (pessoa == null ){
            throw new PessoaInvalidaException("Pessoa é obrigatória");
        }
    }
}
