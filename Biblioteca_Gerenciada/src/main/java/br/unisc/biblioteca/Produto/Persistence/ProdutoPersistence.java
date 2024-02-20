package br.unisc.biblioteca.Produto.Persistence;

import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import br.unisc.biblioteca.Produto.Banco.ProdutoEntity;
import br.unisc.biblioteca.Produto.DTOs.ProdutoDto;
import br.unisc.biblioteca.Produto.DTOs.ProdutosDoFornecedorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProdutoPersistence {

    void criarProduto(Long id, ProdutoDto produtoDto);

    void updateProduto(Long id, ProdutoDto produtoDto);

    void deleteProduto(Long id);

    Page<ProdutoDto> buscarTodosProduto(Pageable pageable);

    Object buscarPorId(Long id);

    Page<ProdutosDoFornecedorDto> buscarProdutosDoFornecedor(Long fornecedorId, Pageable pageable);

    Page<ProdutoEntity> buscarTodos(Pageable pageable);
    
    Page<ProdutoEntity> buscarPorNomeEStatus(String nome, Boolean ativo, Pageable pageable);
}