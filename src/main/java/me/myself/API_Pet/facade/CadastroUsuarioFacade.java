package me.myself.API_Pet.facade;

import me.myself.API_Pet.dto.UsuarioCadastroRequest;
import me.myself.API_Pet.dto.UsuarioCadastroResponse;
import me.myself.API_Pet.model.Pessoa;
import me.myself.API_Pet.model.Usuario;
import me.myself.API_Pet.repository.PessoaRepository;
import me.myself.API_Pet.repository.UsuarioRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CadastroUsuarioFacade {

    private final UsuarioRepository usuarioRepository;
    private final PessoaRepository pessoaRepository;

    public CadastroUsuarioFacade(UsuarioRepository usuarioRepository,
                                 PessoaRepository pessoaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public UsuarioCadastroResponse cadastrar(UsuarioCadastroRequest req) {
        // 1) USUÁRIO
        Usuario usuario = new Usuario()
                .setUser(req.user())
                .setSenha(req.senha());
        usuario = usuarioRepository.save(usuario); // agora tem ID

        // 2) PESSOA
        Pessoa pessoa = new Pessoa();
        pessoa.setNomeCompleto(req.nomeCompleto());
        pessoa.setIdade(req.idade());
        pessoa.setTelefone(req.telefone());
        pessoa.setEndereco(req.endereco());
        if (req.sexo() != null && !req.sexo().isBlank()) {
            try { pessoa.setSexo(Pessoa.Sexo.valueOf(req.sexo())); }
            catch (IllegalArgumentException ignored) { /* se quiser, valide e lance exceção */ }
        }
        pessoa.setUsuario(usuario);
        pessoa = pessoaRepository.save(pessoa);

        // 3) manter relação bidirecional coerente
        usuario.setPessoa(pessoa);

        return new UsuarioCadastroResponse(
                usuario.getId(),
                pessoa.getId(),
                usuario.getUsername(),
                pessoa.getNomeCompleto(),
                pessoa.getEndereco()
        );
    }
}