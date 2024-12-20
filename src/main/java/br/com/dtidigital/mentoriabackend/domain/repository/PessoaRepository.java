package br.com.dtidigital.mentoriabackend.domain.repository;

import br.com.dtidigital.mentoriabackend.domain.entity.Pessoa;
import br.com.dtidigital.mentoriabackend.domain.repository.helper.pessoa.PessoaQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, UUID>, PessoaQueries {
    // Query de exemplo para encontrar uma pessoa pelo apelido e nome (estamos usando de métodos de query do Spring Data JPA)
    @Query("SELECT p FROM Pessoa p WHERE p.apelido = :apelido AND p.nome NOT IN :nomes AND ROWNUM = 1")
    Pessoa findFirstByApelidoAndNomeNotIn(String apelido, List<String> nomes);


}
