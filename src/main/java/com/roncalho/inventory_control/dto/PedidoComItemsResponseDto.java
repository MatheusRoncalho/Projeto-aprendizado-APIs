package com.roncalho.inventory_control.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoComItemsResponseDto(
        Long id,
        String nomeUsuario,
        LocalDateTime dataPedido,
        BigDecimal total,
        List<ItemPedidoDto> itemPedido
) {
}
