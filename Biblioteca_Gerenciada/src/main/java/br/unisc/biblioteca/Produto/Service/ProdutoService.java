package br.unisc.biblioteca.Produto.Service;

import br.unisc.biblioteca.Produto.Banco.ProdutoEntity;
import br.unisc.biblioteca.Produto.DTOs.ProdutoDto;
import br.unisc.biblioteca.Produto.DTOs.ProdutosDoFornecedorDto;
import br.unisc.biblioteca.Produto.Persistence.ProdutoPersistence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Slf4j
@Service
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProdutoService {

    private final ProdutoPersistence persistence;

    public void criarProduto(Long id, ProdutoDto produtoDto) {
        persistence.criarProduto(id, produtoDto);
    }

    public void updateProduto(Long id, ProdutoDto produtoDto) {
        persistence.updateProduto(id, produtoDto);
    }

    public void deleteProduto(Long id) {
        persistence.deleteProduto(id);
    }

    public Page<ProdutoDto> buscarTodosProdutos(Pageable pageable) {
        return persistence.buscarTodosProduto(pageable);
    }

    public Object buscarPorId(Long id) {return persistence.buscarPorId(id);}

    public Page<ProdutosDoFornecedorDto> buscarProdutosDoFornecedor(Long fornecedorId, Pageable pageable) {
        return persistence.buscarProdutosDoFornecedor(fornecedorId, pageable);
    }

    public Page<ProdutoEntity> buscarProdutosPorStatus(boolean ativo, Pageable pageable) {
        return persistence.buscarProdutosPorStatus(ativo, pageable);
    }

    public Page<ProdutoEntity> buscarTodos(Pageable pageable) {
        return persistence.buscarTodos(pageable);
    }

    public Page<ProdutoEntity> buscarPorNome(String nome, Pageable pageable) {
        return persistence.buscarPorNome(nome, pageable);
    }


}