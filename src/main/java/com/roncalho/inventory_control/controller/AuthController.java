package com.roncalho.inventory_control.controller;

import com.roncalho.inventory_control.model.Usuario;
import com.roncalho.inventory_control.security.JwtUtil;
import com.roncalho.inventory_control.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/resgister")
    public ResponseEntity<?> resgister(@RequestBody Usuario request) {
        Usuario usuario = usuarioService.cadastrarUsuario(request.getNomeUsuario(),  request.getEmail(), request.getSenha());
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario request) {
        Usuario usuario = usuarioService.buscarPorNomeUsuario(request.getNomeUsuario());
        if (usuario.getSenha().equals(request.getSenha())) {
            String token = JwtUtil.generateToken(usuario.getEmail());
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }
}
