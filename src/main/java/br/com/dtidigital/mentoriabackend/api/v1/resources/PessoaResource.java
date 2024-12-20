package br.com.dtidigital.mentoriabackend.api.v1.resources;

import br.com.dtidigital.mentoriabackend.api.v1.model.filter.PessoaFiltro;
import br.com.dtidigital.mentoriabackend.api.v1.model.input.PessoaInput;
import br.com.dtidigital.mentoriabackend.api.v1.model.output.PessoaOutput;
import br.com.dtidigital.mentoriabackend.domain.entity.Pessoa;
import br.com.dtidigital.mentoriabackend.domain.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/pessoas")
public class PessoaResource {
    private final PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<Pessoa> create(@RequestBody PessoaInput pessoaInput) {
        Pessoa pessoa = pessoaService.salvar(pessoaInput);

        return ResponseEntity.ok().body(pessoa);
    }

    // Método de validação manual

    // Método para buscar pessoa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPessoaById(@PathVariable UUID id) {

        return null;
    }

    // Método para buscar pessoa pelo Filtro
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/filter")
    public PessoaOutput getPessoaByFilter( PessoaFiltro pessoaFiltro) {
        return pessoaService.filtrar(pessoaFiltro);
    }
}
