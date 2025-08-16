package me.myself.API_Pet.controller;

import me.myself.API_Pet.service.NotificacaoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/design/strategy")
public class StrategyController {
    private final NotificacaoService notificacaoService;
    public StrategyController(NotificacaoService notificacaoService) { this.notificacaoService = notificacaoService; }

    @PostMapping("/notificar")
    public String notificar(@RequestParam Long usuarioId,
                            @RequestParam(defaultValue = "email") String canal,
                            @RequestParam String titulo,
                            @RequestParam String mensagem) {
        notificacaoService.notificarUsuario(usuarioId, canal, mensagem, titulo);
        return "ok";
    }
}
