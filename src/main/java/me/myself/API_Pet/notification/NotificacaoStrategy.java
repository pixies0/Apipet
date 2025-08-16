package me.myself.API_Pet.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

public interface NotificacaoStrategy {
    String canal(); // "email" | "whatsapp" | etc.
    void enviar(String destino, String titulo, String mensagem);
}

@Component
class EmailNotificacaoStrategy implements NotificacaoStrategy {
    private static final Logger log = LoggerFactory.getLogger(EmailNotificacaoStrategy.class);
    public String canal() { return "email"; }
    public void enviar(String destino, String titulo, String mensagem) {
        log.info("[EMAIL] to={} | {} -> {}", destino, titulo, mensagem);
    }
}

@Component
class WhatsappNotificacaoStrategy implements NotificacaoStrategy {
    private static final Logger log = LoggerFactory.getLogger(WhatsappNotificacaoStrategy.class);
    public String canal() { return "whatsapp"; }
    public void enviar(String destino, String titulo, String mensagem) {
        log.info("[WHATSAPP] to={} | {} -> {}", destino, titulo, mensagem);
    }
}
