package br.com.dtidigital.mentoriabackend.domain.service.pessoa.impl;

import br.com.dtidigital.mentoriabackend.api.v1.model.input.PessoaInput;
import br.com.dtidigital.mentoriabackend.domain.exception.PessoaInvalidaException;
import br.com.dtidigital.mentoriabackend.domain.service.ValidarPessoaService;

public class ValidarApelidoPessoaServiceImpl implements ValidarPessoaService {
    @Override
    public void validar(PessoaInput pessoa) {
        if (pessoa.getApelido() == null || pessoa.getApelido().trim().isEmpty()) {
            throw new PessoaInvalidaException("Apelido é obrigatório e não pode ser vazio") ;
        }
        if (pessoa.getApelido().length() > 32) {
            throw new PessoaInvalidaException("Apelido deve ter no máximo 32 caracteres");
        }
    }
}
