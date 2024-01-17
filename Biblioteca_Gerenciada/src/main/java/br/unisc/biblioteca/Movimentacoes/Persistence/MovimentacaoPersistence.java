package br.unisc.biblioteca.Movimentacoes.Persistence;

import br.unisc.biblioteca.Movimentacoes.DTOs.MovimentacaoDTO;
import br.unisc.biblioteca.Produto.DTOs.ProdutoDto;
import br.unisc.biblioteca.Produto.DTOs.ProdutosDoFornecedorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovimentacaoPersistence {


    void criarMovimentacao(MovimentacaoDTO movimentacaoDto);

    void updateMovimentacao(Long id, MovimentacaoDTO movimentacaoDTO);

    void deleteMovimentacao(Long id);

    Page<MovimentacaoDTO> buscarTodasMovimentacoes(Pageable pageable);

    Object buscarPorId(Long id);

}