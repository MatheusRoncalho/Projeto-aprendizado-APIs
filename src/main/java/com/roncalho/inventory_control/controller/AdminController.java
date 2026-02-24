package com.roncalho.inventory_control.controller;

import com.roncalho.inventory_control.model.Usuario;
import com.roncalho.inventory_control.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Admin")
public class AdminController {

    private UsuarioService usuarioService;

    public AdminController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        usuarioService.
    }

    @GetMapping("/usuarios/{id}")
}
