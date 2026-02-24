package com.roncalho.inventory_control.mappers;

import com.roncalho.inventory_control.dto.AdminCreateDto;
import com.roncalho.inventory_control.dto.AdminResponseDto;
import com.roncalho.inventory_control.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    Usuario toUsuario(AdminCreateDto adminCreateDto);

    AdminResponseDto toAdminResponseDto(Usuario usuario);
}
