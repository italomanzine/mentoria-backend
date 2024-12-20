package br.com.dtidigital.mentoriabackend.api.v1.model.input;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PessoaInput {
    private String apelido;
    private String nome;
    private LocalDate nascimento;
    private List<String> stack;
    private UUID id;

    // Getters e Setters
    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public List<String> getStack() {
        return stack;
    }

    public void setStack(List<String> stack) {
        this.stack = stack;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}