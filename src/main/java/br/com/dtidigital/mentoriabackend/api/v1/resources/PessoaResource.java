package br.com.dtidigital.mentoriabackend.api.v1.resources;

import br.com.dtidigital.mentoriabackend.api.v1.model.input.PessoaInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pessoas")
public class PessoaResource {

    // Simulando uma lista em memória para armazenamento temporário
    private List<PessoaInput> pessoas = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody PessoaInput pessoa) {
        // Validações manuais
        String erro = validarPessoa(pessoa);
        if (erro != null) {
            return ResponseEntity.badRequest().body(erro);
        }

        // Verifica se o apelido já existe
        for (Pessoa p : pessoas) {
            if (p.getApelido().equalsIgnoreCase(pessoa.getApelido())) {
                return ResponseEntity.unprocessableEntity().body("Apelido já existe");
            }
        }

        // Gera um UUID para a nova pessoa
        pessoa.setId(UUID.randomUUID());

        // Adiciona à lista simulada
        pessoas.add(pessoa);

        // Construi a URI de localização
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(pessoa.getId())
            .toUri();

        // Retorna 201 Created com o header Location
        return ResponseEntity.created(location).body(pessoa);
    }

    // Método de validação manual

    // Método para buscar pessoa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPessoaById(@PathVariable UUID id) {
        Optional<Pessoa> pessoa = pessoas.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst();

        if (pessoa.isPresent()) {
            return ResponseEntity.ok(pessoa.get());
        } else {
            return ResponseEntity.status(404).body("Pessoa não encontrada");
        }
    }
}
