package com.roncalho.inventory_control.controller;

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

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAllPedidos() {
        return ResponseEntity.ok(pedidoService.findAllPedidos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAllPedidosByClienteId(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.findAllPedidosByUsuarioId(id));
    }

    @GetMapping("/meus-pedidos")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> findMeusPedidos(Authentication authentication) {
        Long usuarioId = ((UsuarioDetails) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(pedidoService.findAllPedidosByUsuarioId(usuarioId));
    }
}
