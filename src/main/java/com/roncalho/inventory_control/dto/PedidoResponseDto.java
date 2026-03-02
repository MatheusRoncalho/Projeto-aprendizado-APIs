package com.roncalho.inventory_control.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResponseDto(
        Long id,
        String nomeUsuario,
        LocalDateTime dataPedido,
        BigDecimal total
) {
}
