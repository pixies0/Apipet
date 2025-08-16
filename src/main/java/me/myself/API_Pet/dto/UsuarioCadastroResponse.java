package me.myself.API_Pet.dto;

public record UsuarioCadastroResponse(
        Long usuarioId,
        Long pessoaId,
        String user,
        String nomeCompleto,
        String endereco
) {}
