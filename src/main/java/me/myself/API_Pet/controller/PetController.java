package me.myself.API_Pet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.myself.API_Pet.model.Pet;
import me.myself.API_Pet.service.PetService;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping
    public List<Pet> listarPets() {
        return petService.listarTodos();
    }

    @GetMapping("/{id}")
    public Pet buscarPorId(@PathVariable Long id) {
        return petService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    @PostMapping
    public Pet criarPet(@RequestBody Pet pet) {
        return petService.salvar(pet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarPet(@PathVariable Long id) {
        @SuppressWarnings("unused")
        Pet pet = petService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Pet com ID " + id + " não encontrado!"));

        petService.deletar(id);
        return ResponseEntity.ok("Pet com ID " + id + " e seus agendamentos foram removidos com sucesso.");
    }


    @PutMapping("/{id}")
    public Pet editarPet(@PathVariable Long id, @RequestBody Pet petAtualizado) {
        return petService.buscarPorId(id)
                .map(pet -> {
                    // Atualizar os campos do Pet
                    pet.setNome(petAtualizado.getNome());
                    pet.setRaca(petAtualizado.getRaca());
                    pet.setSexo(petAtualizado.getSexo());
                    pet.setTamanho(petAtualizado.getTamanho());

                    // Atualizar o relacionamento com Pessoa (se fornecido)
                    if (petAtualizado.getPessoa() != null) {
                        pet.setPessoa(petAtualizado.getPessoa());
                    }

                    // Salvar as alterações
                    return petService.salvar(pet);
                })
                .orElseThrow(() -> new RuntimeException("Pet não encontrado para edição!"));
    }
}
