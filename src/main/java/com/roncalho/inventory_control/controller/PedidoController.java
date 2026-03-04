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

    //B.O / add item em outro carrinho que não seja o seu (só pode adicionar no seu carrinho)
    @PostMapping("/{pedidoId}/produto/{produtoId}")
    public ResponseEntity<?> addItemPedido(@PathVariable Long pedidoId, @PathVariable Long produtoId, @RequestParam Integer quantidade, Authentication authentication) {
        ItemPedidoResponseDto responseDto = pedidoService.addItemPedido(pedidoId, produtoId, quantidade);
        URI location = URI.create(pedidoId+"/produto/"+responseDto.id());
        return ResponseEntity.created(location).body(responseDto);
    }

    //B.O / delete item em outro carrinho que não seja o seu (só pode deletar no seu carrinho)
    @DeleteMapping("/{pedidoId}/produto/{itemPedidoId}")
    public ResponseEntity<?> deleteItemPedido(@PathVariable Long pedidoId, @PathVariable Long itemPedidoId, Authentication authentication) {
        pedidoService.deleteItemPedido(pedidoId, itemPedidoId);
        return ResponseEntity.noContent().build();
    }

    //B.O / delete pedidos que sejam apenas seu
    @DeleteMapping("/{pedidoId}")
    public ResponseEntity<?> deletePedido(@PathVariable Long pedidoId, Authentication authentication) {
        pedidoService.deletePedido(pedidoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAllPedidos() {
        return ResponseEntity.ok(pedidoService.findAllPedidos());
    }

    @GetMapping("/cliente/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAllPedidosByUsuarioId(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.findAllPedidosByUsuarioId(id));
    }

    //Mudar, buscar o pedido por id de tal usuario por id
    @GetMapping("{pedidoId}/cliente/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findPedidoComItensByIdByUsuarioId(@Valid @PathVariable Long pedidoId, @PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.findAllPedidosByUsuarioId(id));
    }

    @GetMapping("/meus-pedidos")
    public ResponseEntity<?> findMeusPedidos(Authentication authentication) {
        Long usuarioId = ((UsuarioDetails) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(pedidoService.findAllPedidosByUsuarioId(usuarioId));
    }

    //Mudar, colocar findPedidoComItensByIdByUsuarioId
    @GetMapping("/meus-pedidos/{pedidoId}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> findMeuPedidoComItensById(Authentication authentication) {
        Long usuarioId = ((UsuarioDetails) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(pedidoService.findAllPedidosByUsuarioId(usuarioId));
    }
}
