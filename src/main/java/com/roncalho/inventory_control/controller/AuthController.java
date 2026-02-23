package com.roncalho.inventory_control.controller;

import com.roncalho.inventory_control.dto.AdminCreateDto;
import com.roncalho.inventory_control.dto.AdminResponseDto;
import com.roncalho.inventory_control.dto.ClienteCreateDto;
import com.roncalho.inventory_control.dto.ClienteResponseDto;
import com.roncalho.inventory_control.model.Usuario;
import com.roncalho.inventory_control.security.JwtUtil;
import com.roncalho.inventory_control.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @PostMapping("/resgister")
    public ResponseEntity<?> resgister(@RequestBody ClienteCreateDto createDto) {
        ClienteResponseDto clienteResponseDto = usuarioService.cadastrarUsuario(createDto);
        return ResponseEntity.ok(clienteResponseDto);
    }

    @PostMapping("/resgister/admin")
    public ResponseEntity<?> resgisterAdmin(@RequestBody AdminCreateDto createDto) {
        AdminResponseDto adminResponseDto = usuarioService.cadastrarUsuarioAdmin(createDto);
        return ResponseEntity.ok(adminResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario request) {
        Usuario usuario = usuarioService.buscarPorNomeUsuario(request.getNomeUsuario());
        if (passwordEncoder.matches(request.getSenha(),usuario.getSenha())) {
            String token = JwtUtil.generateToken(usuario.getEmail());
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }
}
