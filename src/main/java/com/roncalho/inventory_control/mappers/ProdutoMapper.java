package com.roncalho.inventory_control.mappers;

import com.roncalho.inventory_control.dto.ProdutoCreateDto;
import com.roncalho.inventory_control.dto.ProdutoResponseDto;
import com.roncalho.inventory_control.dto.ProdutoUpdateDto;
import com.roncalho.inventory_control.model.Produto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    Produto toProduto(ProdutoCreateDto produtoCreateDto);

    Produto toProduto(ProdutoUpdateDto produtoUpdateDto);

    ProdutoResponseDto toProdutoResponseDto(Produto produto);
}
