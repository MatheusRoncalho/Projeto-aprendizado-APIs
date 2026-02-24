package com.roncalho.inventory_control.service;

import com.roncalho.inventory_control.dto.AdminCreateDto;
import com.roncalho.inventory_control.dto.AdminResponseDto;
import com.roncalho.inventory_control.dto.ClienteCreateDto;
import com.roncalho.inventory_control.dto.ClienteResponseDto;
import com.roncalho.inventory_control.exceptions.RecursoNaoEncontradoException;
import com.roncalho.inventory_control.mappers.AdminMapper;
import com.roncalho.inventory_control.mappers.ClienteMapper;
import com.roncalho.inventory_control.model.Role;
import com.roncalho.inventory_control.model.Usuario;
import com.roncalho.inventory_control.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    public final UsuarioRepository usuarioRepository;
    public final ClienteMapper clienteMapper;
    public final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;


    public UsuarioService(UsuarioRepository usuarioRepository, ClienteMapper clienteMapper, AdminMapper adminMapper) {
        this.usuarioRepository = usuarioRepository;
        this.clienteMapper = clienteMapper;
        this.adminMapper = adminMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public ClienteResponseDto cadastrarUsuario(ClienteCreateDto createDto) {
        Role role = usuarioRepository.findById(1L).isEmpty() ? Role.ADMIN : Role.CLIENTE;
        Usuario usuario = clienteMapper.toUsuario(createDto);
        usuario.setSenha(passwordEncoder.encode(createDto.senha()));
        usuario.setRole(role);
        usuarioRepository.save(usuario);
        return clienteMapper.toClienteResponseDto(usuario);
    }

    public AdminResponseDto cadastrarUsuarioAdmin(AdminCreateDto createDto) {
        Usuario usuario = adminMapper.toUsuario(createDto);
        usuarioRepository.save(usuario);
        return adminMapper.toAdminResponseDto(usuario);
    }

    public Usuario buscarPorNomeUsuario(String nomeUsuario){
        return usuarioRepository.findByNomeUsuario(nomeUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario com nome " + nomeUsuario + " não existe"));
    }

    public List<ClienteResponseDto> buscarTodosUsuarios(){
        return usuarioRepository.findAll()
                .stream()
                .map(clienteMapper::toClienteResponseDto)
                .toList();
    }

    public ClienteResponseDto buscarClientePorId(Long id){
        return usuarioRepository.findById(id).map(clienteMapper::toClienteResponseDto)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente com " + id + " não encontrado"));
    }

}
