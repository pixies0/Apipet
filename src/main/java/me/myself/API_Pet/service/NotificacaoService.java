package me.myself.API_Pet.service;

import me.myself.API_Pet.notification.NotificacaoStrategy;
import me.myself.API_Pet.repository.UsuarioRepository;
import me.myself.API_Pet.shared.AuditorLog;

import org.springframework.stereotype.Service;
import java.util.Map;


@Service
public class NotificacaoService {

    private final Map<String, NotificacaoStrategy> estrategiasPorCanal;
    private final UsuarioRepository usuarioRepository;

    public NotificacaoService(
            java.util.List<NotificacaoStrategy> estrategias,
            UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.estrategiasPorCanal = estrategias.stream()
                .collect(java.util.stream.Collectors.toMap(NotificacaoStrategy::canal, e -> e));
    }

    public void notificarUsuario(Long usuarioId, String canal, String titulo, String mensagem) {
        var usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + usuarioId));

        var strategy = estrategiasPorCanal.getOrDefault(canal, estrategiasPorCanal.get("email"));
        strategy.enviar(usuario.getUsername(), titulo, mensagem);

        AuditorLog.INSTANCE.log("system", "NOTIFICAR",
                "usuarioId=%d canal=%s".formatted(usuarioId, canal));
    }
}
