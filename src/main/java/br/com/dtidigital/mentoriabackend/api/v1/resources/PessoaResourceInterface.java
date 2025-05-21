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
                examples = @ExampleObject(value = """
                        {
                        	"id": "63d47ea3-1cc8-494e-9b71-8b9d593d804b",
                        	"apelido": "Jonny",
                        	"nome": "João Nascimento",
                        	"nascimento": "1985-09-23",
                        	"stacks": [
                        		"C++"
                        	]
                        }
                    """))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                  "status": 400,
                  "timestamp": "2025-05-14T11:27:24.7838306-03:00",
                  "title": "Invalid data",
                  "userMessage": "The request body is invalid. Check JSON syntax.",
                  "detail": "The request body is invalid. Check syntax error."
                }
            """))),
        @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                   "status": 500,
                   "timestamp": "2025-05-14T11:33:54.8518141-03:00",
                   "title": "Internal server error",
                   "userMessage": "Internal server error",
                   "detail": "An internal server error occurred."
                 }
            """)))
    })
    ResponseEntity<Pessoa> create(@RequestBody PessoaInput pessoaInput);

    @GetMapping("/{id}")
    @Operation(summary = "Busca pessoa por ID", description = "Retorna os dados da pessoa ou 404 se não existir")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa encontrada",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                        "id": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
                        "apelido": "Ciclano",
                        "nome": "Ciclano de Souza",
                        "nascimento": "1995-07-12",
                        "stack": ["Python", "Flask"]
                    }
                """))),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "timestamp": "2025-05-14T11:30:00.0000000-03:00",
                      "title": "Resource not found",
                      "userMessage": "Pessoa não encontrada.",
                      "detail": "Pessoa com ID especificado não foi encontrada."
                    }
                """))),
        @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                       "status": 500,
                       "timestamp": "2025-05-14T11:33:54.8518141-03:00",
                       "title": "Internal server error",
                       "userMessage": "Internal server error",
                       "detail": "An internal server error occurred."
                     }
                """)))
    })
    ResponseEntity<PessoaOutput> getPessoaById(@PathVariable String id);

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza pessoa", description = "Recebe ID e dados de entrada para atualizar a pessoa")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                        "id": "b2c3d4e5-f6a7-8901-2345-678901bcdef0",
                        "apelido": "BeltranoAtualizado",
                        "nome": "Beltrano Silva Atualizado",
                        "nascimento": "1980-03-30",
                        "stacks": ["Ruby", "Rails", "JavaScript"]
                    }
                """))),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "timestamp": "2025-05-14T11:31:00.0000000-03:00",
                      "title": "Resource not found",
                      "userMessage": "Pessoa não encontrada para atualização.",
                      "detail": "Pessoa com ID especificado não foi encontrada para atualização."
                    }
                """))),
        @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                       "status": 500,
                       "timestamp": "2025-05-14T11:33:54.8518141-03:00",
                       "title": "Internal server error",
                       "userMessage": "Internal server error",
                       "detail": "An internal server error occurred during update."
                     }
                """)))
    })
    ResponseEntity<Pessoa> updatePessoa(@PathVariable String id, @RequestBody PessoaInput pessoaInput);

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove pessoa", description = "Deleta a pessoa pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pessoa removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "timestamp": "2025-05-14T11:32:00.0000000-03:00",
                      "title": "Resource not found",
                      "userMessage": "Pessoa não encontrada para exclusão.",
                      "detail": "Pessoa com ID especificado não foi encontrada para exclusão."
                    }
                """))),
        @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                       "status": 500,
                       "timestamp": "2025-05-14T11:33:54.8518141-03:00",
                       "title": "Internal server error",
                       "userMessage": "Internal server error",
                       "detail": "An internal server error occurred during deletion."
                     }
                """)))
    })
    ResponseEntity<Void> deletePessoa(@PathVariable String id);

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Filtra pessoa", description = "Retorna pessoa de acordo com critérios de filtro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Filtro aplicado com sucesso",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                        "id": "c3d4e5f6-a7b8-9012-3456-789012cdef01",
                        "apelido": "Filtrado",
                        "nome": "Pessoa Filtrada Exemplo",
                        "nascimento": "2000-12-01",
                        "stack": ["PHP", "Laravel"]
                    }
                """))),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "status": 400,
                      "timestamp": "2025-05-14T11:35:00.0000000-03:00",
                      "title": "Invalid parameter",
                      "userMessage": "Parâmetros de filtro inválidos.",
                      "detail": "Um ou mais parâmetros fornecidos para o filtro são inválidos."
                    }
                """))),
        @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                       "status": 500,
                       "timestamp": "2025-05-14T11:33:54.8518141-03:00",
                       "title": "Internal server error",
                       "userMessage": "Internal server error",
                       "detail": "An internal server error occurred during filtering."
                     }
                """)))
    })
    PessoaOutput getPessoaByFilter(PessoaFiltro pessoaFiltro);
}