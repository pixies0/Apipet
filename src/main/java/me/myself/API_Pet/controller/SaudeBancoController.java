package me.myself.API_Pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SaudeBancoController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/banco")
    public String testarConexao() {
        try {
            jdbcTemplate.execute("SELECT 1");
            return "Conexão bem-sucedida com o banco de dados!";
        } catch (Exception e) {
            return "Erro ao conectar com o banco de dados: " + e.getMessage();
        }
    }
}
