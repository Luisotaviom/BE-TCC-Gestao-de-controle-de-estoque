package br.unisc.biblioteca.Produto.Service;

import br.unisc.biblioteca.Produto.DTOs.ProdutoDto;
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

    public void criarProduto(ProdutoDto produtoDto) {
        persistence.criarProduto(produtoDto);
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

}