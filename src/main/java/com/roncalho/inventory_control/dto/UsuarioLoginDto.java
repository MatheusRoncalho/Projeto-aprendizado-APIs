package com.roncalho.inventory_control.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioLoginDto(
        @NotBlank(message = "Nome não pode ser vazio") String nomeUsuario,
        @NotNull(message = "Senha invalida") String senha
) {
}
