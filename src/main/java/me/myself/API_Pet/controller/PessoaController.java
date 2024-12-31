package me.myself.API_Pet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

import me.myself.API_Pet.model.Pessoa;
import me.myself.API_Pet.service.PessoaService;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public List<Pessoa> listarPessoas() {
        return pessoaService.listarTodos();
    }

    @GetMapping("/{id}")
    public Pessoa buscarPorId(@PathVariable Long id) {
        return pessoaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    @PostMapping
    public Pessoa criarPessoa(@RequestBody Pessoa pessoa) {
        return pessoaService.salvar(pessoa);
    }

    @DeleteMapping("/{id}")
    public void deletarPet(@PathVariable Long id) {
        pessoaService.deletar(id);
    }
}
