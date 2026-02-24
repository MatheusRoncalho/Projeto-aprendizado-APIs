package com.roncalho.inventory_control.mappers;

import com.roncalho.inventory_control.dto.ClienteCreateDto;
import com.roncalho.inventory_control.dto.ClienteResponseDto;
import com.roncalho.inventory_control.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Usuario toUsuario(ClienteCreateDto clienteCreateDto);

    ClienteResponseDto toClienteResponseDto(Usuario usuario);
}
