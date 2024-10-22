package br.com.dtidigital.mentoriabackend.domain.service;

import br.com.dtidigital.mentoriabackend.api.v1.model.input.PessoaInput;

public interface ValidarPessoaService {
    void validar(PessoaInput pessoa);
}
