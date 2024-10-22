package br.com.dtidigital.mentoriabackend.domain.service.pessoa.impl;

import br.com.dtidigital.mentoriabackend.api.v1.model.input.PessoaInput;
import br.com.dtidigital.mentoriabackend.domain.exception.PessoaInvalidaException;
import br.com.dtidigital.mentoriabackend.domain.service.ValidarPessoaService;

public class ValidarDataNascimentoPessoaServiceImpl implements ValidarPessoaService {
    @Override
    public void validar(PessoaInput pessoa) {
        if (pessoa.getNascimento() == null || !pessoa.getNascimento().matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new PessoaInvalidaException("Nascimento é obrigatório e deve estar no formato AAAA-MM-DD");
        }
    }
}
