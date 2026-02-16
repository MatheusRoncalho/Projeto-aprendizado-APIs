package com.roncalho.inventory_control.dto;

public record ProdutoResponseDto(
        long id,
        String nome,
        double preco,
        int quantidade
) {
}
