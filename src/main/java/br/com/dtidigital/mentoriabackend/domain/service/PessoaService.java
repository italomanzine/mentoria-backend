package br.com.dtidigital.mentoriabackend.domain.service;

import br.com.dtidigital.mentoriabackend.api.v1.model.filter.PessoaFiltro;
import br.com.dtidigital.mentoriabackend.api.v1.model.input.PessoaInput;
import br.com.dtidigital.mentoriabackend.api.v1.model.output.PessoaOutput;
import br.com.dtidigital.mentoriabackend.domain.entity.Pessoa;
import org.springframework.stereotype.Service;

@Service
public interface PessoaService {
    Pessoa salvar(PessoaInput pessoa);
    PessoaOutput filtrar(PessoaFiltro pessoaFiltro);
}
