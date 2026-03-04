package com.roncalho.inventory_control.repository;

import com.roncalho.inventory_control.dto.PedidoResponseDto;
import com.roncalho.inventory_control.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findAllByUsuarioId(Long usuarioId);

    Optional<Pedido> findByIdAndUsuarioId(Long id, Long usuarioId);
}