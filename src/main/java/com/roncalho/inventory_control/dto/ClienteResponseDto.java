package com.roncalho.inventory_control.dto;

public record ClienteResponseDto(
        Long id,
        String nomeUsuario,
        String email
) {
}
