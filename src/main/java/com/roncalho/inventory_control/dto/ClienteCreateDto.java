package com.roncalho.inventory_control.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteCreateDto(
        @NotBlank(message = "Nome não pode ser vazio") String nomeUsuario,
        @NotBlank(message = "Email não pode ser vazio")
        @Email(message = "Email inválido") String email,
        @NotNull(message = "Senha inválida") String senha
) {
}
