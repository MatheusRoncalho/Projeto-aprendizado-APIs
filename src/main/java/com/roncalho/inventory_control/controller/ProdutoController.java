package com.roncalho.inventory_control.controller;

import com.roncalho.inventory_control.dto.ProdutoCreateDto;
import com.roncalho.inventory_control.dto.ProdutoResponseDto;
import com.roncalho.inventory_control.dto.ProdutoUpdateDto;
import com.roncalho.inventory_control.model.Produto;
import com.roncalho.inventory_control.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDto> saveProduto(@Valid @RequestBody ProdutoCreateDto createDto) {
        ProdutoResponseDto responseDto = produtoService.saveProduto(createDto);
        URI location = URI.create("/produtos/"+responseDto.id());

        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDto>> findAllProdutos(){
        return ResponseEntity.ok(produtoService.findAllProdutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> findProdutoById(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.findProdutoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> updateProduto(@PathVariable Long id, @Valid @RequestBody ProdutoUpdateDto updateDto){
        ProdutoResponseDto atualizado = produtoService.updateProduto(id, updateDto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id){
        produtoService.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }
}
