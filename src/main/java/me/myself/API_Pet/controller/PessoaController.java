package me.myself.API_Pet.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.myself.API_Pet.model.Pessoa;
import me.myself.API_Pet.model.Usuario;
import me.myself.API_Pet.repository.PessoaRepository;
import me.myself.API_Pet.repository.UsuarioRepository;
import me.myself.API_Pet.service.PessoaService;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Pessoa> listarPessoas() {
        return pessoaService.listarTodos();
    }

    @GetMapping("/{id}")
    public Pessoa buscarPorId(@PathVariable Long id) {
        return pessoaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoaAtualizada) {
        return pessoaRepository.findById(id)
                .map(pessoa -> {
                    // Atualizar os campos básicos
                    pessoa.setNomeCompleto(pessoaAtualizada.getNomeCompleto());
                    pessoa.setIdade(pessoaAtualizada.getIdade());
                    pessoa.setEndereco(pessoaAtualizada.getEndereco());
                    pessoa.setTelefone(pessoaAtualizada.getTelefone());
                    pessoa.setSexo(pessoaAtualizada.getSexo());

                    // Atualizar o relacionamento com Usuario, se necessário
                    if (pessoaAtualizada.getUsuario() != null && pessoaAtualizada.getUsuario().getId() != null) {
                        Optional<Usuario> usuario = usuarioRepository.findById(pessoaAtualizada.getUsuario().getId());
                        if (usuario.isPresent()) {
                            pessoa.setUsuario(usuario.get());
                        } else {
                            return ResponseEntity.badRequest().body("Usuário com ID " + pessoaAtualizada.getUsuario().getId() + " não encontrado.");
                        }
                    }

                    // Salvar e retornar a pessoa atualizada
                    pessoaRepository.save(pessoa);
                    return ResponseEntity.ok("Pessoa com ID " + id + " atualizada com sucesso.");
                })
                .orElseThrow(() -> new RuntimeException("Pessoa com ID " + id + " não encontrada!"));
    }

}
