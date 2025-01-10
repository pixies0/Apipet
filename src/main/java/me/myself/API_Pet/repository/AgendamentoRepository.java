package me.myself.API_Pet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.myself.API_Pet.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByPetId(Long petId);
    void deleteByPetId(Long petId);
}
