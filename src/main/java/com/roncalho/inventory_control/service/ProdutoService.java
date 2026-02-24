package com.roncalho.inventory_control.service;

import com.roncalho.inventory_control.dto.ProdutoCreateDto;
import com.roncalho.inventory_control.dto.ProdutoResponseDto;
import com.roncalho.inventory_control.dto.ProdutoUpdateDto;
import com.roncalho.inventory_control.exceptions.RecursoNaoEncontradoException;
import com.roncalho.inventory_control.mappers.ProdutoMapper;
import com.roncalho.inventory_control.model.Produto;
import com.roncalho.inventory_control.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    public ProdutoResponseDto saveProduto(ProdutoCreateDto createDto){
        Produto produto = produtoMapper.toProduto(createDto);
        produtoRepository.save(produto);
        return produtoMapper.toProdutoResponseDto(produto);
    }

    public List<ProdutoResponseDto> findAllProdutos(){
        List<Produto> produtoList = produtoRepository.findAll();
        return produtoList
                .stream()
                .map(produtoMapper::toProdutoResponseDto)
                .toList();
    }

    public ProdutoResponseDto findProdutoById(Long id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto com ID "+id+" não encontrado"));
        return produtoMapper.toProdutoResponseDto(produto);
    }

    public ProdutoResponseDto updateProduto(Long id, ProdutoUpdateDto dadosUpdateDto){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto com ID "+id+" não encontrado"));

        produto.setNome(dadosUpdateDto.nome());
        produto.setPreco(dadosUpdateDto.preco());
        produtoRepository.save(produto);

        return produtoMapper.toProdutoResponseDto(produto);
    }

    public void deleteProduto(Long id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto com ID "+id+" não encontrado"));
        produtoRepository.delete(produto);
    }
}
