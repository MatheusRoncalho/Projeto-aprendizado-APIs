package com.roncalho.inventory_control.mappers;

import com.roncalho.inventory_control.dto.PedidoResponseDto;
import com.roncalho.inventory_control.model.Pedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    PedidoResponseDto ToPedidoResponseDto(Pedido pedido);
}
