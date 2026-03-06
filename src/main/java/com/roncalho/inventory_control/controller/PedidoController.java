package com.roncalho.inventory_control.controller;

import com.roncalho.inventory_control.dto.ItemPedidoResponseDto;
import com.roncalho.inventory_control.dto.PedidoResponseDto;
import com.roncalho.inventory_control.security.UsuarioDetails;
import com.roncalho.inventory_control.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<?> savePedido(Authentication authentication) {
        Long usuarioId = ((UsuarioDetails) authentication.getPrincipal()).getId();
        PedidoResponseDto responseDto = pedidoService.savePedido(usuarioId);
        URI location = URI.create("/produtos/"+responseDto.id());
        return ResponseEntity.created(location).body(responseDto);
    }

    @PostMapping("/{pedidoId}/produto/{produtoId}")
    public ResponseEntity<?> addItemPedido(@PathVariable Long pedidoId, @PathVariable Long produtoId, @RequestParam Integer quantidade, Authentication authentication) {
        Long usuarioId = ((UsuarioDetails) authentication.getPrincipal()).getId();
        ItemPedidoResponseDto responseDto = pedidoService.addItemPedido(pedidoId, produtoId, quantidade, usuarioId);
        URI location = URI.create(pedidoId+"/produto/"+responseDto.id());
        return ResponseEntity.created(location).body(responseDto);
    }

    //B.O / delete item em outro carrinho que não seja o seu (só pode deletar no seu carrinho)
    @DeleteMapping("/{pedidoId}/produto/{itemPedidoId}")
    public ResponseEntity<?> deleteItemPedido(@PathVariable Long pedidoId, @PathVariable Long itemPedidoId, Authentication authentication) {
        Long usuarioId = ((UsuarioDetails) authentication.getPrincipal()).getId();
        pedidoService.deleteItemPedido(pedidoId, itemPedidoId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{pedidoId}")
    public ResponseEntity<?> deletePedido(@PathVariable Long pedidoId, Authentication authentication) {
        Long usuarioId = ((UsuarioDetails) authentication.getPrincipal()).getId();
        pedidoService.deletePedido(pedidoId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAllPedidos() {
        return ResponseEntity.ok(pedidoService.findAllPedidos());
    }

    @GetMapping("/cliente/{clienteId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAllPedidosByUsuarioId(@Valid @PathVariable Long clienteId) {
        return ResponseEntity.ok(pedidoService.findAllPedidosByUsuarioId(clienteId));
    }

    @GetMapping("{pedidoId}/items")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findPedidoComItensByPedidoId(@Valid @PathVariable Long pedidoId) {
        return ResponseEntity.ok(pedidoService.findPedidoComItensByPedidoId(pedidoId));
    }

    @GetMapping("/meus-pedidos")
    public ResponseEntity<?> findMeusPedidos(Authentication authentication) {
        Long usuarioId = ((UsuarioDetails) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(pedidoService.findAllPedidosByUsuarioId(usuarioId));
    }

    @GetMapping("/meus-pedidos/{pedidoId}/items")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> findMeuPedidoComItensById(@Valid @PathVariable Long pedidoId, Authentication authentication) {
        Long usuarioId = ((UsuarioDetails) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(pedidoService.findPedidoComItensByPedidoIdByUsuarioId(pedidoId, usuarioId));
    }
}
