package com.roncalho.inventory_control.controller;

import com.roncalho.inventory_control.dto.*;
import com.roncalho.inventory_control.model.Usuario;
import com.roncalho.inventory_control.security.JwtUtil;
import com.roncalho.inventory_control.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/register")
    public ResponseEntity<?> resgister(@Valid @RequestBody ClienteCreateDto createDto) {
        ClienteResponseDto clienteResponseDto = usuarioService.cadastrarUsuario(createDto);
        URI location = URI.create("/auth/register/"+clienteResponseDto.id());
        return ResponseEntity.ok(clienteResponseDto);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> resgisterAdmin(@Valid @RequestBody AdminCreateDto createDto) {
        AdminResponseDto adminResponseDto = usuarioService.cadastrarUsuarioAdmin(createDto);
        URI location = URI.create("/auth/register/admin/"+adminResponseDto.id());
        return ResponseEntity.ok(adminResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UsuarioLoginDto loginDto) {
        Usuario usuario = usuarioService.buscarPorNomeUsuario(loginDto.nomeUsuario());
        if (passwordEncoder.matches(loginDto.senha(), usuario.getSenha())) {
            String token = JwtUtil.generateToken(usuario.getEmail());
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }
}
