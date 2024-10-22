package br.com.dtidigital.mentoriabackend.domain.service;

import br.com.dtidigital.mentoriabackend.api.v1.model.input.PessoaInput;
import br.com.dtidigital.mentoriabackend.domain.entity.Pessoa;

public interface PessoaService {
    Pessoa salvar(PessoaInput pessoa);
}
