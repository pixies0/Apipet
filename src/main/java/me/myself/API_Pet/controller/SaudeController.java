package me.myself.API_Pet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class SaudeController {
    @GetMapping("/saude")
    public String saude() {
        return "O Servidor está Funcionando";
    }
}
