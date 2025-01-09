package me.myself.API_Pet.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import me.myself.API_Pet.repository.UsuarioRepository;
import me.myself.API_Pet.service.PessoaService;
import me.myself.API_Pet.service.UsuarioService;
import me.myself.API_Pet.dto.LoginUsuarioDto;
import me.myself.API_Pet.dto.RegistroUsuarioDto;
import me.myself.API_Pet.model.Pessoa;
import me.myself.API_Pet.model.Usuario;

@Service
public class AuthenticationService {

    private final UsuarioRepository userRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PessoaService pessoaService;

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
        Usuario novoUsuario = usuarioService.salvar(user);

        // 2. Criar e salvar a Pessoa associada ao Usuario
        Pessoa pessoa = new Pessoa();
        pessoa.setNomeCompleto(input.getNomeCompleto());
        pessoa.setIdade(input.getIdade());
        pessoa.setEndereco(input.getEndereco());
        pessoa.setTelefone(input.getTelefone());
        pessoa.setSexo(input.getSexo());
        pessoa.setUsuario(novoUsuario);

        pessoaService.salvar(pessoa);

        return user;
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
