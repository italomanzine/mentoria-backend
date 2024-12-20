package br.com.dtidigital.mentoriabackend.domain.repository.helper.pessoa;

import br.com.dtidigital.mentoriabackend.api.v1.model.filter.PessoaFiltro;
import br.com.dtidigital.mentoriabackend.domain.entity.Pessoa;

public interface PessoaQueries {
    Pessoa filtro(PessoaFiltro pessoaFiltro);
}
