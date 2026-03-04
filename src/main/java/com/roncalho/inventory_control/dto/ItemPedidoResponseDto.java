package com.roncalho.inventory_control.dto;

import java.math.BigDecimal;

public record ItemPedidoResponseDto(
        Long id,
        ProdutoResumoDto produto,
        int quantidade,
        BigDecimal preco
) {
}
