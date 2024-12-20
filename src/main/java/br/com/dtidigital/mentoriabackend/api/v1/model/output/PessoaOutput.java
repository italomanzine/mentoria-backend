package br.com.dtidigital.mentoriabackend.api.v1.model.output;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PessoaOutput (
     String apelido,
     String nome,
     LocalDate nascimento,
     List<String> stack,
     UUID id
){}