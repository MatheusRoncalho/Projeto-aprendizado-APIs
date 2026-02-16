package com.roncalho.inventory_control.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProdutoUpdateDto(
        @NotBlank(message = "Nome não pode ser vazio")
        String nome,
        @NotNull(message = "Preco não pode ser null")
        @Positive(message = "Preco não pode ser negativo")
        double preco
) {
}
