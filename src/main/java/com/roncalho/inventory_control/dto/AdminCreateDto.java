package com.roncalho.inventory_control.dto;

import com.roncalho.inventory_control.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminCreateDto(
        @NotBlank String nomeUsuario,
        @NotBlank(message = "Email não pode ser vazio")
        @Email(message = "Email inválido") String email,
        @NotNull(message = "Senha não pode ser vazia") String senha,
        @NotNull(message = "role não pode ser vazio") Role role
) {
}
