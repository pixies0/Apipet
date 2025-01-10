package me.myself.API_Pet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.myself.API_Pet.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByPessoaId(Long pessoaId);
}
