package br.unisc.biblioteca.Produto.Persistence;

import br.unisc.biblioteca.Produto.DTOs.ProdutoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoPersistence {

    void criarProduto(Long id, ProdutoDto produtoDto);

    void updateProduto(Long id, ProdutoDto produtoDto);

    void deleteProduto(Long id);

    Page<ProdutoDto> buscarTodosProduto(Pageable pageable);

    Object buscarPorId(Long id);
}