package com.roncalho.inventory_control.repository;

import com.roncalho.inventory_control.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Item_pedidoRepository extends JpaRepository<ItemPedido, Long> {
}