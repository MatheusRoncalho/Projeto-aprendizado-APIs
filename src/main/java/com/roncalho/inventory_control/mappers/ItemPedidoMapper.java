package com.roncalho.inventory_control.mappers;

import com.roncalho.inventory_control.dto.ItemPedidoResponseDto;
import com.roncalho.inventory_control.dto.PedidoComItemsResponseDto;
import com.roncalho.inventory_control.model.ItemPedido;
import com.roncalho.inventory_control.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedidoResponseDto ToItemPedidoResponseDto(ItemPedido itemPedido);
    //ARRUMAR ESSE MAPPER PARA RETORNAR UM PEDIDO COM ITEMS
    @Mapping(source = "usuario.nome", target = "nomeUsuario")
    @Mapping(source = "itensPedido", target = "itensPedido")
    PedidoComItemsResponseDto toPedidoComItemsResponseDto(Pedido pedido);
}
