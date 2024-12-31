package me.myself.API_Pet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.myself.API_Pet.model.Pessoa;
import me.myself.API_Pet.repository.PessoaRepository;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa salvar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> listarTodos() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> buscarPorId(Long id) {
        return pessoaRepository.findById(id);
    }

    public void deletar(Long id) {
        pessoaRepository.deleteById(id);
    }
}
