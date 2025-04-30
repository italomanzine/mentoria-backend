package br.com.dtidigital.mentoriabackend.domain.service;

import org.springframework.stereotype.Service;

import br.com.dtidigital.mentoriabackend.api.v1.model.filter.PessoaFiltro;
import br.com.dtidigital.mentoriabackend.api.v1.model.input.PessoaInput;
import br.com.dtidigital.mentoriabackend.api.v1.model.output.PessoaOutput;
import br.com.dtidigital.mentoriabackend.domain.entity.Pessoa;

@Service
public interface PessoaService {
    Pessoa salvar(PessoaInput pessoa);
    PessoaOutput filtrar(PessoaFiltro pessoaFiltro);
    PessoaOutput buscarPorId(String id);
    Pessoa atualizar(String id, PessoaInput pessoaInput);
    void deletar(String id);
}
