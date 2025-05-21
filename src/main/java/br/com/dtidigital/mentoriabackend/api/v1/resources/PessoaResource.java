package br.com.dtidigital.mentoriabackend.api.v1.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.dtidigital.mentoriabackend.api.v1.model.filter.PessoaFiltro;
import br.com.dtidigital.mentoriabackend.api.v1.model.input.PessoaInput;
import br.com.dtidigital.mentoriabackend.api.v1.model.output.PessoaOutput;
import br.com.dtidigital.mentoriabackend.domain.entity.Pessoa;
import br.com.dtidigital.mentoriabackend.domain.service.PessoaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(onConstructor = @__(
        @Autowired))
@RequestMapping("/api/v1/pessoas")
public class PessoaResource implements PessoaResourceInterface {

    private final PessoaService pessoaService;

    @Override
    public ResponseEntity<Pessoa> create(@RequestBody PessoaInput pessoaInput) {
        Pessoa pessoa = pessoaService.salvar(pessoaInput);
        if (1 == 1) {
            throw new InternalError("Erro interno");
        }
        return ResponseEntity.ok().body(pessoa);
    }

    // Método de validação manual
    // Método para buscar pessoa por ID
    @Override
    public ResponseEntity<PessoaOutput> getPessoaById(@PathVariable String id) {
        PessoaOutput pessoa = pessoaService.buscarPorId(id);
        if (pessoa == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pessoa);
    }

    // Método para atualizar pessoa
    @Override
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable String id, @RequestBody PessoaInput pessoaInput) {
        Pessoa pessoaAtualizada = pessoaService.atualizar(id, pessoaInput);
        if (pessoaAtualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pessoaAtualizada);
    }

    // Método para deletar pessoa
    @Override
    public ResponseEntity<Void> deletePessoa(@PathVariable String id) {
        pessoaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Método para buscar pessoa pelo Filtro
    @Override
    public PessoaOutput getPessoaByFilter(PessoaFiltro pessoaFiltro) {
        return pessoaService.filtrar(pessoaFiltro);
    }
}
