package br.com.dtidigital.mentoriabackend.domain.service.mapper;

import br.com.dtidigital.mentoriabackend.api.v1.model.input.PessoaInput;
import br.com.dtidigital.mentoriabackend.api.v1.model.output.PessoaOutput;
import br.com.dtidigital.mentoriabackend.domain.entity.Pessoa;
import org.springframework.stereotype.Component;

@Component
public class PessoaMapper {

    public Pessoa toEntity(PessoaInput pessoaInput) {
        Pessoa pessoa = new Pessoa();
        pessoa.setApelido(pessoaInput.getApelido());
        pessoa.setNome(pessoaInput.getNome());
        pessoa.setNascimento(pessoaInput.getNascimento());
        pessoa.setStacks(pessoaInput.getStack());
        pessoa.setId(pessoaInput.getId());
        return pessoa;
    }

    public PessoaOutput toOutput(Pessoa pessoa) {
        return new PessoaOutput(
                pessoa.getApelido(),
                pessoa.getNome(),
                pessoa.getNascimento(),
                pessoa.getStacks(),
                pessoa.getId()
        );

    }
}
