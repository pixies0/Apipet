package me.myself.API_Pet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.myself.API_Pet.model.Usuario;
import me.myself.API_Pet.repository.PessoaRepository;
import me.myself.API_Pet.repository.UsuarioRepository;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Autowired
    private PessoaRepository pessoaRepository;


    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public void deletarUsuario(Long id) {
        // Verificar se o usuário existe
        @SuppressWarnings("unused")
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário com ID " + id + " não encontrado!"));

        // Excluir a Pessoa associada
        pessoaRepository.deleteByUsuarioId(id);

        // Excluir o Usuario
        usuarioRepository.deleteById(id);
    }

}
