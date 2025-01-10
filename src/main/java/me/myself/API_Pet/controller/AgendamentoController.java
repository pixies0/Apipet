package me.myself.API_Pet.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.myself.API_Pet.dto.AgendamentoDto;
import me.myself.API_Pet.model.Agendamento;
import me.myself.API_Pet.model.Pet;
import me.myself.API_Pet.model.Servico;
import me.myself.API_Pet.repository.AgendamentoRepository;
import me.myself.API_Pet.repository.PetRepository;
import me.myself.API_Pet.repository.ServicoRepository;
import me.myself.API_Pet.service.AgendamentoService;

@RestController
@RequestMapping("/api/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    private final AgendamentoRepository agendamentoRepository;
    private final ServicoRepository servicoRepository;
    private final PetRepository petRepository;

    public AgendamentoController(
            AgendamentoRepository agendamentoRepository,
            ServicoRepository servicoRepository, PetRepository petRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.servicoRepository = servicoRepository;
        this.petRepository = petRepository;
    }

    @GetMapping
    public List<Agendamento> listarPessoas() {
        return agendamentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Agendamento buscarPorId(@PathVariable Long id) {
        return agendamentoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criarAgendamento(@RequestBody AgendamentoDto agendamentoDTO) {
        // Buscar o serviço no banco de dados
        Optional<Servico> servico = servicoRepository.findById(agendamentoDTO.getServicoId());
        if (!servico.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Serviço com ID " + agendamentoDTO.getServicoId() + " não encontrado.");
        }

        Optional<Pet> pet = petRepository.findById(agendamentoDTO.getPetId());
        if (!pet.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Pet com ID " + agendamentoDTO.getPetId() + " não encontrado.");
        }

        // Criar o objeto Agendamento
        Agendamento agendamento = new Agendamento();
        agendamento.setUsuarioId(agendamentoDTO.getUsuarioId());
        agendamento.setServico(servico.get()); // Associar o objeto Servico
        agendamento.setPet(pet.get());
        agendamento.setStatus(agendamentoDTO.getStatus());

        // Salvar no banco
        agendamentoRepository.save(agendamento);

        return ResponseEntity.status(HttpStatus.CREATED).body("Agendamento criado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarAgendamento(@PathVariable Long id, @RequestBody Agendamento agendamentoAtualizado) {
        return agendamentoRepository.findById(id)
                .map(agendamento -> {
                    // Atualizar os campos básicos
                    agendamento.setUsuarioId(agendamentoAtualizado.getUsuarioId());

                    // Validar e atualizar o serviço
                    if (agendamentoAtualizado.getServico() != null
                            && agendamentoAtualizado.getServico().getId() != null) {
                        Optional<Servico> servico = servicoRepository
                                .findById(agendamentoAtualizado.getServico().getId());
                        if (servico.isPresent()) {
                            agendamento.setServico(servico.get());
                        } else {
                            return ResponseEntity.badRequest().body("Serviço com ID "
                                    + agendamentoAtualizado.getServico().getId() + " não encontrado.");
                        }
                    }

                    // Validar e atualizar o pet
                    if (agendamentoAtualizado.getPet() != null && agendamentoAtualizado.getPet().getId() != null) {
                        Optional<Pet> pet = petRepository.findById(agendamentoAtualizado.getPet().getId());
                        if (pet.isPresent()) {
                            agendamento.setPet(pet.get());
                        } else {
                            return ResponseEntity.badRequest()
                                    .body("Pet com ID " + agendamentoAtualizado.getPet().getId() + " não encontrado.");
                        }
                    }

                    // Atualizar o status
                    if (agendamentoAtualizado.getStatus() != null) {
                        agendamento.setStatus(agendamentoAtualizado.getStatus());
                    }

                    // Salvar e retornar o agendamento atualizado
                    agendamentoRepository.save(agendamento);
                    return ResponseEntity.ok("Agendamento atualizado com sucesso.");
                })
                .orElseThrow(() -> new RuntimeException("Agendamento com ID " + id + " não encontrado!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAgendamento(@PathVariable Long id) {
        return agendamentoRepository.findById(id)
                .map(agendamento -> {
                    agendamentoRepository.deleteById(id);
                    return ResponseEntity.ok("Agendamento com ID " + id + " foi removido com sucesso.");
                })
                .orElseThrow(() -> new RuntimeException("Agendamento com ID " + id + " não encontrado!"));
    }

}
