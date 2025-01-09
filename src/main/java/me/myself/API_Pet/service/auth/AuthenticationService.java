package me.myself.API_Pet.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import me.myself.API_Pet.repository.UsuarioRepository;
import me.myself.API_Pet.dto.LoginUsuarioDto;
import me.myself.API_Pet.dto.RegistroUsuarioDto;
import me.myself.API_Pet.model.Usuario;

@Service
public class AuthenticationService {
    private final UsuarioRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UsuarioRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario signup(RegistroUsuarioDto input) {
        Usuario user = new Usuario()
                .setUser(input.getUser())
                .setSenha(passwordEncoder.encode(input.getSenha()));

        return userRepository.save(user);
    }

    public Usuario authenticate(LoginUsuarioDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUser(),
                        input.getSenha()
                )
        );

        return userRepository.findByUser(input.getUser())
                .orElseThrow();
    }
}
