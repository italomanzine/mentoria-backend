package br.com.dtidigital.mentoriabackend.domain.service.pessoa.impl;

import br.com.dtidigital.mentoriabackend.api.v1.model.filter.PessoaFiltro;
import br.com.dtidigital.mentoriabackend.api.v1.model.input.PessoaInput;
import br.com.dtidigital.mentoriabackend.api.v1.model.output.PessoaOutput;
import br.com.dtidigital.mentoriabackend.domain.entity.Pessoa;
import br.com.dtidigital.mentoriabackend.domain.repository.PessoaRepository;
import br.com.dtidigital.mentoriabackend.domain.service.PessoaService;
import br.com.dtidigital.mentoriabackend.domain.service.ValidarPessoaService;
import br.com.dtidigital.mentoriabackend.domain.service.mapper.PessoaMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PessoaServiceImpl implements PessoaService {
    private ValidarPessoaService validarPessoaService;
    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;

    /*@Autowired
    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }*/

    @Override
    public Pessoa salvar(PessoaInput pessoa) {

        validarPessoaService = new ValidarPessoaServiceImpl();
        validarPessoaService.validar(pessoa);

        validarPessoaService = new ValidarApelidoPessoaServiceImpl();
        validarPessoaService.validar(pessoa);

        validarPessoaService = new ValidarDataNascimentoPessoaServiceImpl();
        validarPessoaService.validar(pessoa);

        validarPessoaService = new ValidarNomePessoaServiceImpl();
        validarPessoaService.validar(pessoa);

        validarPessoaService = new ValidarStackPessoaServiceImpl();
        validarPessoaService.validar(pessoa);

        return pessoaRepository.save(pessoaMapper.toEntity(pessoa));
    }

    @Override
    public PessoaOutput filtrar(PessoaFiltro pessoaFiltro) {
        Pessoa pessoa = pessoaRepository.filtro(pessoaFiltro);
        if(pessoa == null) {
            return null;
        }
        return new PessoaOutput(pessoa.getApelido(), pessoa.getNome(), pessoa.getNascimento(), pessoa.getStacks(),
            pessoa.getId());
    }

    @Override
    public PessoaOutput buscarPorId(String id) {
        Pessoa pessoa = pessoaRepository.findById(UUID.fromString(id)).orElse(null);
        if(pessoa == null) {
            return null;
        }
        return new PessoaOutput(pessoa.getApelido(), pessoa.getNome(), pessoa.getNascimento(), pessoa.getStacks(),
            pessoa.getId());
    }

    @Override
    public Pessoa atualizar(String id, PessoaInput pessoaInput) {
        Pessoa pessoa = pessoaRepository.findById(UUID.fromString(id)).orElse(null);
        if (pessoa == null) {
            throw new EntityNotFoundException("Pessoa não encontrada");
        }

        pessoaInput.setId(pessoa.getId());
        return salvar(pessoaInput);
    }

    @Override
    public void deletar(String id) {
        pessoaRepository.deleteById(UUID.fromString(id));
    }
}
