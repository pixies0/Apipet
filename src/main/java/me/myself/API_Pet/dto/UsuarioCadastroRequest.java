package me.myself.API_Pet.dto;

public record UsuarioCadastroRequest(
        String user,
        String senha,
        String nomeCompleto,
        Integer idade,
        String telefone,
        String sexo,
        String endereco
) {}
