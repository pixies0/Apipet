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
    public void deletarPet(@PathVariable Long id) {
        petService.deletar(id);
    }
}
