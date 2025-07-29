package br.com.dtidigital.mentoriabackend.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PessoaInput {
    @Schema(description = "Apelido da pessoa", example = "Jhonny")
    @NotNull(message = "Apelido é obrigatório")
    private String apelido;

    @Schema(description = "Nome completo da pessoa", example = "Jhonny Doe")
    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @Schema(description = "Data de nascimento da pessoa", example = "1990-01-01")
    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDate nascimento;

    @Schema(description = "Lista de tecnologias que a pessoa conhece", example = """
        ["Java", "Javascript"]
        """)
    @NotNull(message = "Stack é obrigatória")
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