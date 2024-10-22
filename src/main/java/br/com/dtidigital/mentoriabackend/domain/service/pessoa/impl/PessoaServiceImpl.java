package br.com.dtidigital.mentoriabackend.domain.service.pessoa.impl;

import br.com.dtidigital.mentoriabackend.api.v1.model.input.PessoaInput;
import br.com.dtidigital.mentoriabackend.domain.entity.Pessoa;
import br.com.dtidigital.mentoriabackend.domain.repository.PessoaRepository;
import br.com.dtidigital.mentoriabackend.domain.service.PessoaService;
import br.com.dtidigital.mentoriabackend.domain.service.ValidarPessoaService;
import br.com.dtidigital.mentoriabackend.domain.service.mapper.PessoaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
