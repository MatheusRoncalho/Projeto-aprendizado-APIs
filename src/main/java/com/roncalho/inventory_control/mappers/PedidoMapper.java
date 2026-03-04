package com.roncalho.inventory_control.mappers;

import com.roncalho.inventory_control.dto.PedidoComItemsResponseDto;
import com.roncalho.inventory_control.dto.PedidoResponseDto;
import com.roncalho.inventory_control.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ItemPedidoMapper.class)
public interface PedidoMapper {

    @Mapping(source = "usuario.nomeUsuario", target = "nomeUsuario")
    PedidoResponseDto ToPedidoResponseDto(Pedido pedido);

    @Mapping(source = "usuario.nomeUsuario", target = "nomeUsuario")
    @Mapping(source = "itens", target = "itensPedido")
    PedidoComItemsResponseDto toPedidoComItemsResponseDto(Pedido pedido);
}
