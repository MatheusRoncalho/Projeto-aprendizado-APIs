package com.roncalho.inventory_control.service;

import com.roncalho.inventory_control.dto.ItemPedidoResponseDto;
import com.roncalho.inventory_control.dto.PedidoComItemsResponseDto;
import com.roncalho.inventory_control.dto.PedidoResponseDto;
import com.roncalho.inventory_control.exceptions.RecursoNaoEncontradoException;
import com.roncalho.inventory_control.mappers.ItemPedidoMapper;
import com.roncalho.inventory_control.mappers.PedidoMapper;
import com.roncalho.inventory_control.model.ItemPedido;
import com.roncalho.inventory_control.model.Pedido;
import com.roncalho.inventory_control.model.Produto;
import com.roncalho.inventory_control.model.Usuario;
import com.roncalho.inventory_control.repository.ItemPedidoRepository;
import com.roncalho.inventory_control.repository.PedidoRepository;
import com.roncalho.inventory_control.repository.ProdutoRepository;
import com.roncalho.inventory_control.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoMapper pedidoMapper;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoMapper  itemPedidoMapper;

    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository, PedidoMapper pedidoMapper, ItemPedidoRepository itemPedidoRepository, ProdutoRepository produtoRepository, ItemPedidoMapper itemPedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pedidoMapper = pedidoMapper;
        this.itemPedidoRepository = itemPedidoRepository;
        this.produtoRepository = produtoRepository;
        this.itemPedidoMapper = itemPedidoMapper;
    }

    public PedidoResponseDto savePedido(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario Não encontrado"));
        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .total(BigDecimal.ZERO)
                .build();
        pedidoRepository.save(pedido);
        return pedidoMapper.ToPedidoResponseDto(pedido);
    }

    public List<PedidoResponseDto> findAllPedidos() {
        return pedidoRepository.findAll().stream()
                .map(pedidoMapper::ToPedidoResponseDto)
                .toList();
    }

    public List<PedidoResponseDto> findAllPedidosByUsuarioId(Long usuarioId) {
        return pedidoRepository.findAllByUsuarioId(usuarioId).stream()
                .map(pedidoMapper::ToPedidoResponseDto)
                .toList();
    }

    //FAZER ESSE METODO PARA ENCONTRAR O PEDIDO CMO ITENS POR ID DE TAL USUARIO ID QUE VAI SER PASSADO
    public PedidoComItemsResponseDto findPedidoComItensByIdByUsuarioId(Long pedidoId, Long usuarioId) {
        Pedido pedido = pedidoRepository.findByIdAndUsuarioId(pedidoId, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));
        return pedidoMapper.toPedidoComItemsResponseDto(pedido);
    }

    @Transactional
    public ItemPedidoResponseDto addItemPedido(Long pedidoId, Long produtoId, Integer quantidade) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));
        ItemPedido itemPedido = ItemPedido.builder()
                .pedido(pedido)
                .produto(produto)
                .quantidade(quantidade)
                .preco(BigDecimal.valueOf(produto.getPreco()))
                .build();
        itemPedidoRepository.save(itemPedido);

        pedido.setTotal(pedido.getTotal().add(
                BigDecimal.valueOf(produto.getPreco()).multiply(BigDecimal.valueOf(quantidade))));
        pedidoRepository.save(pedido);
        return itemPedidoMapper.ToItemPedidoResponseDto(itemPedido);
    }

    @Transactional
    public void deleteItemPedido(Long pedidoId, Long itemPedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));
        ItemPedido itemPedido = itemPedidoRepository.findByIdAndPedidoId(itemPedidoId, pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("item não encontrado"));

        pedido.setTotal(pedido.getTotal().subtract(
                itemPedido.getPreco().multiply(BigDecimal.valueOf(itemPedido.getQuantidade()))));
        pedidoRepository.save(pedido);
        itemPedidoRepository.delete(itemPedido);
    }

    public void deletePedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));
        pedidoRepository.delete(pedido);
    }

//    public PedidoComItemsResponseDto findPedidoComItemsById(Long pedidoId) {
//
//    }
}
