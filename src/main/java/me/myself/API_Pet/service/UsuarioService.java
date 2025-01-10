package me.myself.API_Pet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.myself.API_Pet.model.Pet;
import me.myself.API_Pet.model.Usuario;
import me.myself.API_Pet.repository.AgendamentoRepository;
import me.myself.API_Pet.repository.PessoaRepository;
import me.myself.API_Pet.repository.PetRepository;
import me.myself.API_Pet.repository.UsuarioRepository;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;


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
        // Buscar o usuário
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário com ID " + id + " não encontrado!"));

        // Excluir os agendamentos relacionados aos pets
        List<Pet> pets = petRepository.findByPessoaId(usuario.getPessoa().getId());
        pets.forEach(pet -> agendamentoRepository.deleteByPetId(pet.getId()));

        // Excluir os pets relacionados à pessoa
        petRepository.deleteAll(pets);

        // Excluir a pessoa associada ao usuário
        pessoaRepository.deleteById(usuario.getPessoa().getId());

        // Excluir o usuário
        usuarioRepository.deleteById(id);
    }


}
