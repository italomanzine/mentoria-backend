package br.com.dtidigital.mentoriabackend.domain.repository;

import br.com.dtidigital.mentoriabackend.domain.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {
}
