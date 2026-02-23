package com.roncalho.inventory_control.service;

import com.roncalho.inventory_control.dto.AdminCreateDto;
import com.roncalho.inventory_control.dto.AdminResponseDto;
import com.roncalho.inventory_control.dto.ClienteCreateDto;
import com.roncalho.inventory_control.dto.ClienteResponseDto;
import com.roncalho.inventory_control.exceptions.RecursoNaoEncontradoException;
import com.roncalho.inventory_control.model.Role;
import com.roncalho.inventory_control.model.Usuario;
import com.roncalho.inventory_control.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    public final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public ClienteResponseDto cadastrarUsuario(ClienteCreateDto createDto) {
        Role role = usuarioRepository.findById(1L).isEmpty() ? Role.ADMIN : Role.CLIENTE;
        Usuario usuario = Usuario.builder()
                .nomeUsuario(createDto.nomeUsuario())
                .email(createDto.email())
                .senha(passwordEncoder.encode(createDto.senha()))
                .role(role)
                .build();
        usuarioRepository.save(usuario);
        return new ClienteResponseDto(
                usuario.getId(), usuario.getNomeUsuario(), usuario.getEmail());
    }

    public AdminResponseDto cadastrarUsuarioAdmin(AdminCreateDto createDto) {
        Usuario usuario = Usuario.builder()
                .nomeUsuario(createDto.nomeUsuario())
                .email(createDto.email())
                .senha(passwordEncoder.encode(createDto.senha()))
                .role(createDto.role())
                .build();
        usuarioRepository.save(usuario);
        return new AdminResponseDto(
                usuario.getId(), usuario.getNomeUsuario(), usuario.getEmail(), usuario.getSenha(), usuario.getRole());
    }

    public Usuario buscarPorNomeUsuario(String nomeUsuario){
        return usuarioRepository.findByNomeUsuario(nomeUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario com nome " + nomeUsuario + " não existe"));
    }

}
