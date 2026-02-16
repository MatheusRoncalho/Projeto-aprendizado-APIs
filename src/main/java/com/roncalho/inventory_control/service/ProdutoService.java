package com.roncalho.inventory_control.service;

import com.roncalho.inventory_control.dto.ProdutoCreateDto;
import com.roncalho.inventory_control.dto.ProdutoResponseDto;
import com.roncalho.inventory_control.dto.ProdutoUpdateDto;
import com.roncalho.inventory_control.model.Produto;
import com.roncalho.inventory_control.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoResponseDto saveProduto(ProdutoCreateDto createDto){
        Produto produto = new Produto();
        produto.setNome(createDto.nome());
        produto.setPreco(createDto.preco());
        produto.setQuantidade(createDto.quantidade());
        produtoRepository.save(produto);
        return new ProdutoResponseDto(
                produto.getId(), produto.getNome(), produto.getPreco(), produto.getQuantidade());
    }

    public List<ProdutoResponseDto> findAllProdutos(){
        List<Produto> produtoList = produtoRepository.findAll();
        return produtoList.stream()
                .map(produto -> new ProdutoResponseDto(
                        produto.getId(), produto.getNome(), produto.getPreco(), produto.getQuantidade())).toList();
    }

    public ProdutoResponseDto findProdutoById(Long id){
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (optionalProduto.isEmpty()) throw new EntityNotFoundException("Produto não encontrado");
        Produto produto = optionalProduto.get();
        return new ProdutoResponseDto(
                produto.getId(), produto.getNome(), produto.getPreco(), produto.getQuantidade());
    }

    public ProdutoResponseDto updateProduto(Long id, ProdutoUpdateDto dadosUpdateDto){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        produto.setNome(dadosUpdateDto.nome());
        produto.setPreco(dadosUpdateDto.preco());
        produtoRepository.save(produto);

        return new ProdutoResponseDto(
                produto.getId(), produto.getNome(), produto.getPreco(), produto.getQuantidade());
    }

    public void deleteProduto(Long id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        produtoRepository.delete(produto);
    }
}
