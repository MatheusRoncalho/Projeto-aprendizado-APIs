package com.roncalho.inventory_control.controller;

import com.roncalho.inventory_control.dto.ClienteResponseDto;
import com.roncalho.inventory_control.model.Usuario;
import com.roncalho.inventory_control.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private UsuarioService usuarioService;

    public AdminController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<ClienteResponseDto>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.buscarTodosClientes());
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> buscarClientePorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
    }
}
