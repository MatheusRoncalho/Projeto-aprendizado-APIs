package com.roncalho.inventory_control.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;
    
    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private int quantidade;

    @OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
    private Set<ItemPedido> itens;
}
