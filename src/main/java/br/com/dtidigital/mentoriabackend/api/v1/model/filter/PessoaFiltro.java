package br.com.dtidigital.mentoriabackend.api.v1.model.filter;

import java.time.LocalDate;

public record PessoaFiltro(
    String apelido,
    String nome,
    LocalDate dataNascimento
) {}
