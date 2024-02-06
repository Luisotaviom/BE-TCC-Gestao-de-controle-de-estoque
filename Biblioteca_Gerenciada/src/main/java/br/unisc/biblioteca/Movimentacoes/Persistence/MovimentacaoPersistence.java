package br.unisc.biblioteca.Movimentacoes.Persistence;

import br.unisc.biblioteca.Movimentacoes.Banco.MovimentacaoEntity;
import br.unisc.biblioteca.Movimentacoes.DTOs.*;
import br.unisc.biblioteca.Movimentacoes.Persistence.Exceptions.ValidacaoNegocioException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface MovimentacaoPersistence {


    void criarMovimentacao(MovimentacaoDTO movimentacaoDto) throws ValidacaoNegocioException;

    void updateMovimentacao(Long id, MovimentacaoDTO movimentacaoDTO);

    void deleteMovimentacao(Long id);

    Page<MovimentacaoDetalhesDTO> buscarTodasMovimentacoes(Pageable pageable);

    Object buscarPorId(Long id);

    Page<MovimentacaoEntity> buscarPorTipo(String tipo, Pageable pageable);

    Page<MovimentacaoDTO> buscarPorIntervaloDeData(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<MovimentacaoDTO> buscarPorTipoEData(String tipo, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<MovimentacaoDetalhesDTO> buscarMovimentacoesSemanais(Pageable pageable);

    Page<MovimentacaoDetalhesDTO> buscarMovimentacoesMensais(Pageable pageable);

    BigDecimal calcularSomaValorPorTipoEData(String tipo, LocalDateTime start, LocalDateTime end);

    Integer calcularSomaQuantidadePorTipoEData(String tipo, LocalDateTime start, LocalDateTime end);

    RelatorioSemanalSaldoDTO calcularRelatorioSemanalSaldo();

    RelatorioSemanalEntradasDTO calcularRelatorioSemanalEntradas();

    RelatorioSemanalSaidasDTO calcularRelatorioSemanalSaidas();

    Page<MovimentacaoDetalhesDTO> buscarMovimentacoesPorTipoECategoriaEData(String tipo, String categoria, LocalDateTime start, LocalDateTime end, Pageable pageable);

}