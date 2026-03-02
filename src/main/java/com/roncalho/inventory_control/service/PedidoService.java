package com.roncalho.inventory_control.service;

import com.roncalho.inventory_control.dto.PedidoResponseDto;
import com.roncalho.inventory_control.exceptions.RecursoNaoEncontradoException;
import com.roncalho.inventory_control.mappers.PedidoMapper;
import com.roncalho.inventory_control.model.Pedido;
import com.roncalho.inventory_control.model.Usuario;
import com.roncalho.inventory_control.repository.Item_pedidoRepository;
import com.roncalho.inventory_control.repository.PedidoRepository;
import com.roncalho.inventory_control.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoMapper pedidoMapper;
    private final Item_pedidoRepository item_pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository, PedidoMapper pedidoMapper, Item_pedidoRepository itemPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pedidoMapper = pedidoMapper;
        item_pedidoRepository = itemPedidoRepository;
    }

    public PedidoResponseDto savePedido(Long usuarioId) {
        Usuario usuario = usuarioRepository.getReferenceById(usuarioId);
        Pedido pedido = Pedido.builder().usuario(usuario).build();
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
}
