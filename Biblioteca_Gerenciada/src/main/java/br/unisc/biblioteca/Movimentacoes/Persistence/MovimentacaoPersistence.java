package br.unisc.biblioteca.Movimentacoes.Persistence;

import br.unisc.biblioteca.Movimentacoes.Banco.MovimentacaoEntity;
import br.unisc.biblioteca.Movimentacoes.DTOs.MovimentacaoDTO;
import br.unisc.biblioteca.Movimentacoes.Persistence.Exceptions.ValidacaoNegocioException;
import br.unisc.biblioteca.Produto.DTOs.ProdutoDto;
import br.unisc.biblioteca.Produto.DTOs.ProdutosDoFornecedorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface MovimentacaoPersistence {


    void criarMovimentacao(MovimentacaoDTO movimentacaoDto) throws ValidacaoNegocioException;

    void updateMovimentacao(Long id, MovimentacaoDTO movimentacaoDTO);

    void deleteMovimentacao(Long id);

    Page<MovimentacaoDTO> buscarTodasMovimentacoes(Pageable pageable);

    Object buscarPorId(Long id);

    Page<MovimentacaoEntity> buscarPorTipo(String tipo, Pageable pageable);

    Page<MovimentacaoDTO> buscarPorIntervaloDeData(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<MovimentacaoDTO> buscarPorTipoEData(String tipo, LocalDateTime start, LocalDateTime end, Pageable pageable);

}