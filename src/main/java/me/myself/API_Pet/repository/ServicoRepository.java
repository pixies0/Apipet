package me.myself.API_Pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.myself.API_Pet.model.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
}
