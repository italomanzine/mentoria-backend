package br.com.dtidigital.mentoriabackend.domain.service.pessoa.impl;

import br.com.dtidigital.mentoriabackend.api.v1.model.input.PessoaInput;
import br.com.dtidigital.mentoriabackend.domain.exception.PessoaInvalidaException;
import br.com.dtidigital.mentoriabackend.domain.service.ValidarPessoaService;

public class ValidarNomePessoaServiceImpl implements ValidarPessoaService {
    @Override
    public void validar(PessoaInput pessoa) {
        if (pessoa.getNome() == null || pessoa.getNome().trim().isEmpty()) {
            throw new PessoaInvalidaException("Nome é obrigatório e não pode ser vazio");
        }
        if (pessoa.getNome().length() > 100) {
            throw new PessoaInvalidaException("Nome deve ter no máximo 100 caracteres");
        }
    }
}
