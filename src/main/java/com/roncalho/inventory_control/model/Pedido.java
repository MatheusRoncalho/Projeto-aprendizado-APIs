package com.roncalho.inventory_control.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "data_pedido", nullable = false)
    private LocalDateTime dataPedido;

    @OneToMany(mappedBy = "pedido",  fetch = FetchType.LAZY)
    private Set<Item_pedido> itens;
}
