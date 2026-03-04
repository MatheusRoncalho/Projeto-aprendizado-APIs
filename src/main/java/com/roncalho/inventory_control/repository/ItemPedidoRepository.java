package com.roncalho.inventory_control.repository;

import com.roncalho.inventory_control.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    Optional<ItemPedido> findByIdAndPedidoId(Long itemPedidoId, Long pedidoId);
}