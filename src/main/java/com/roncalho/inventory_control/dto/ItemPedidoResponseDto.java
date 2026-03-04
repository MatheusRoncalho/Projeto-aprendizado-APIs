package com.roncalho.inventory_control.dto;

import com.roncalho.inventory_control.model.Produto;

import java.math.BigDecimal;

public record ItemPedidoResponseDto(
        Long id,
        Produto produto,
        int quantidade,
        BigDecimal preco
) {
}
