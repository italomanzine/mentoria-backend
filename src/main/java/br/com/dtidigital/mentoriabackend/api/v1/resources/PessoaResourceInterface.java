package br.com.dtidigital.mentoriabackend.api.v1.resources;

import br.com.dtidigital.mentoriabackend.api.v1.model.filter.PessoaFiltro;
import br.com.dtidigital.mentoriabackend.api.v1.model.input.PessoaInput;
import br.com.dtidigital.mentoriabackend.api.v1.model.output.PessoaOutput;
import br.com.dtidigital.mentoriabackend.domain.entity.Pessoa;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@Tag(name = "Pessoa", description = "API para gerenciamento de pessoas")
@RequestMapping("/api/v1/pessoas")
public interface PessoaResourceInterface {

    @PostMapping
    @Operation(summary = "Cria nova pessoa", description = "Recebe dados de entrada e retorna a pessoa criada")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa criada com sucesso",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = "{\"id\":\"123\",\"nome\":\"Fulano\"}"))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<Pessoa> create(@RequestBody PessoaInput pessoaInput);

    @GetMapping("/{id}")
    @Operation(summary = "Busca pessoa por ID", description = "Retorna os dados da pessoa ou 404 se não existir")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa encontrada",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = "{\"id\":\"123\",\"nome\":\"Fulano\"}"))),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<PessoaOutput> getPessoaById(@PathVariable String id);

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza pessoa", description = "Recebe ID e dados de entrada para atualizar a pessoa")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = "{\"id\":\"123\",\"nome\":\"Novo Nome\"}"))),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<Pessoa> updatePessoa(@PathVariable String id, @RequestBody PessoaInput pessoaInput);

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove pessoa", description = "Deleta a pessoa pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pessoa removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<Void> deletePessoa(@PathVariable String id);

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Filtra pessoa", description = "Retorna pessoa de acordo com critérios de filtro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Filtro aplicado com sucesso",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = "{\"id\":\"123\",\"nome\":\"Fulano\"}"))),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(mediaType = "application/json"))
    })
    PessoaOutput getPessoaByFilter(PessoaFiltro pessoaFiltro);
}