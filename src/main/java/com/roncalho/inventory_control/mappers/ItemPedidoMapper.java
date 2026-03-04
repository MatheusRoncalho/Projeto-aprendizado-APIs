package com.roncalho.inventory_control.mappers;

import com.roncalho.inventory_control.dto.ItemPedidoResponseDto;
import com.roncalho.inventory_control.model.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProdutoMapper.class)
public interface ItemPedidoMapper {

    @Mapping(source = "produto", target = "produto")
    ItemPedidoResponseDto ToItemPedidoResponseDto(ItemPedido itemPedido);
}
