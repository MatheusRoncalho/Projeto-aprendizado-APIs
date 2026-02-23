package com.roncalho.inventory_control.dto;

import com.roncalho.inventory_control.model.Role;

public record AdminResponseDto(
        Long id,
        String nomeUsuario,
        String email,
        String senha,
        Role role
) {
}
