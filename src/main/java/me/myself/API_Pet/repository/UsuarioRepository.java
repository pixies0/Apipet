package me.myself.API_Pet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.myself.API_Pet.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUser(String user);
}
