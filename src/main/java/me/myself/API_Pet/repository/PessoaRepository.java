package me.myself.API_Pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.myself.API_Pet.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
