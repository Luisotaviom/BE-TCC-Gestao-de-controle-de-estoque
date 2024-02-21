package br.unisc.biblioteca.Movimentacoes.Persistence;

import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import br.unisc.biblioteca.Fornecedor.Repository.FornecedorRepository;
import br.unisc.biblioteca.Movimentacoes.Banco.MovimentacaoEntity;
import br.unisc.biblioteca.Movimentacoes.DTOs.*;
import br.unisc.biblioteca.Movimentacoes.Persistence.Exceptions.EntidadeNaoEncontradaException;
import br.unisc.biblioteca.Movimentacoes.Persistence.Exceptions.ValidacaoNegocioException;
import br.unisc.biblioteca.Movimentacoes.Repository.MovimentacaoRepository;
import br.unisc.biblioteca.Produto.Banco.ProdutoEntity;
import br.unisc.biblioteca.Produto.Repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Component
public class MovimentacaoPersistenceAdapter implements MovimentacaoPersistence {

    @Autowired
    private final MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private ProdutoRepository produtoRepository;



    @Override
    public void criarMovimentacao(MovimentacaoDTO movimentacaoDto) throws ValidacaoNegocioException {
        Optional<FornecedorEntity> fornecedorOptional = fornecedorRepository.findById(movimentacaoDto.getFornecedor_id());
        if (fornecedorOptional.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Fornecedor não encontrado com ID: " + movimentacaoDto.getFornecedor_id());
        }
        FornecedorEntity fornecedor = fornecedorOptional.get();
        Optional<ProdutoEntity> produtoOptional = produtoRepository.findById(movimentacaoDto.getProduto_id());
        if (produtoOptional.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado com ID: " + movimentacaoDto.getProduto_id());
        }
        ProdutoEntity produto = produtoOptional.get();

        if (!produto.getFornecedor().getId().equals(fornecedor.getId())) {
            throw new ValidacaoNegocioException("O produto selecionado não pertence ao fornecedor selecionado.");
        }

        MovimentacaoEntity movimentacaoEntidade = MovimentacaoEntity.criarEntidade(movimentacaoDto, fornecedor, produto);

        movimentacaoRepository.save(movimentacaoEntidade);
    }

    @Override
    public void updateMovimentacao(Long id, MovimentacaoDTO movimentacaoDto) {
        Optional<MovimentacaoEntity> entidadeOptional = movimentacaoRepository.findById(id);
        MovimentacaoEntity entidade = entidadeOptional.get();

        entidade.setQuantidade(movimentacaoDto.getQuantidade() != null ? movimentacaoDto.getQuantidade() : entidade.getQuantidade());
        entidade.setValor(movimentacaoDto.getValor() != null ? movimentacaoDto.getValor() : entidade.getValor());
        entidade.setTipo(StringUtils.isNotBlank(movimentacaoDto.getTipo()) ? movimentacaoDto.getTipo() : entidade.getTipo());
        entidade.setDataRegistro(movimentacaoDto.getDataRegistro() != null ? movimentacaoDto.getDataRegistro() : entidade.getDataRegistro());

        if (movimentacaoDto.getFornecedor_id() != null) {
            Optional<FornecedorEntity> fornecedorOptional = fornecedorRepository.findById(movimentacaoDto.getFornecedor_id());
            FornecedorEntity fornecedor = fornecedorOptional.orElse(null);
            entidade.setFornecedor(fornecedor);
        }

        if (movimentacaoDto.getProduto_id() != null) {
            Optional<ProdutoEntity> produtoOptional = produtoRepository.findById(movimentacaoDto.getProduto_id());
            ProdutoEntity produto = produtoOptional.orElse(null);
            entidade.setProduto(produto);
        }

        movimentacaoRepository.save(entidade);
    }

    @Override
    public void deleteMovimentacao(Long id) {
        movimentacaoRepository.deleteById(id);
    }

    public MovimentacaoDTO buscarPorId(Long id) {
        Optional<MovimentacaoEntity> movimentacaoOptional = movimentacaoRepository.findById(id);

        if (movimentacaoOptional.isPresent()) {
            MovimentacaoEntity movimentacao = movimentacaoOptional.get();
            return MovimentacaoEntity.convertEntidadeParaDto(movimentacao);
        } else {
            return null;
        }
    }

    @Override
    public Page<MovimentacaoDetalhesDTO> buscarTodasMovimentacoes(Pageable pageable) {
        return movimentacaoRepository.findAll(pageable)
                .map(MovimentacaoEntity::convertEntidadeParaDto);
    }

    @Override
    public Page<MovimentacaoEntity> buscarPorTipo(String tipo, Pageable pageable) {
        if ("E".equals(tipo) || "S".equals(tipo)) {
            return movimentacaoRepository.findByTipo(tipo, pageable);
        } else {
            return movimentacaoRepository.findAll(pageable);
        }
    }

    @Override
    public Page<MovimentacaoEntity> buscarPorCategoria(String categoria, Pageable pageable) {
        if ("Gas".equals(categoria) || "Agua".equals(categoria)) {
            return movimentacaoRepository.findByProdutoCategoria(categoria, pageable);
        } else {
            return movimentacaoRepository.findAll(pageable);
        }
    }

    @Override
    public Page<MovimentacaoDTO> buscarPorIntervaloDeData(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        Page<MovimentacaoEntity> page = movimentacaoRepository.findAllByDataRegistroBetween(start, end, pageable);
        return page.map(MovimentacaoEntity::convertEntidadeParaDto);
    }

    @Override
    public Page<MovimentacaoDTO> buscarPorTipoEData(String tipo, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        Page<MovimentacaoEntity> page = movimentacaoRepository.findByTipoAndDataRegistroBetween(tipo, start, end, pageable);
        return page.map(MovimentacaoEntity::convertEntidadeParaDto);
    }

    @Override
    public Page<MovimentacaoDetalhesDTO> buscarMovimentacoesSemanais(Pageable pageable) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioDaSemana = agora.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atStartOfDay();
        LocalDateTime fimDaSemana = agora.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).toLocalDate().atTime(23, 59);

        return movimentacaoRepository.findAllByDataRegistroBetween(inicioDaSemana, fimDaSemana, pageable)
                .map(MovimentacaoEntity::convertEntidadeParaDto);
    }


    @Override
    public Page<MovimentacaoDetalhesDTO> buscarMovimentacoesMensais(Pageable pageable) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioDoMes = agora.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate().atStartOfDay();
        LocalDateTime fimDoMes = agora.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate().atTime(23, 59);

        return movimentacaoRepository.findAllByDataRegistroBetween(inicioDoMes, fimDoMes, pageable)
                .map(MovimentacaoEntity::convertEntidadeParaDto);
    }

    @Override
    public Page<MovimentacaoDetalhesDTO> buscarMovimentacoesPorTipoECategoriaEData(String tipo, String categoria, LocalDateTime start, LocalDateTime end, Pageable pageable) {

        return movimentacaoRepository.buscarMovimentacoesPorTipoECategoriaEData(
                tipo,
                categoria,
                start,
                end,
                pageable).map(MovimentacaoEntity::convertEntidadeParaDto);
    }

    @Override
    public BigDecimal calcularSomaValorPorTipoEData(String tipo, LocalDateTime start, LocalDateTime end) {
        return movimentacaoRepository.calcularSomaValorPorTipoEData(tipo, start, end);
    }

    @Override
    public Integer calcularSomaQuantidadePorTipoEData(String tipo, LocalDateTime inicio, LocalDateTime fim) {
        return movimentacaoRepository.calcularSomaQuantidadePorTipoEData(tipo, inicio, fim);
    }

    @Override
    public RelatorioSemanalEntradasDTO calcularRelatorioSemanalEntradas() {
        LocalDateTime inicioDaSemana = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).toLocalDate().atStartOfDay();
        LocalDateTime fimDaSemana = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)).toLocalDate().atTime(23, 59);

        BigDecimal somaValorEntradas = calcularSomaValorPorTipoEData("E", inicioDaSemana, fimDaSemana);
        somaValorEntradas = somaValorEntradas != null ? somaValorEntradas : BigDecimal.ZERO;

        Integer somaQuantidadeEntradas = calcularSomaQuantidadePorTipoEData("E", inicioDaSemana, fimDaSemana);
        somaQuantidadeEntradas = somaQuantidadeEntradas != null ? somaQuantidadeEntradas : 0;

        return new RelatorioSemanalEntradasDTO(somaQuantidadeEntradas, somaValorEntradas);
    }

    @Override
    public RelatorioSemanalSaidasDTO calcularRelatorioSemanalSaidas() {
        LocalDateTime inicioDaSemana = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).toLocalDate().atStartOfDay();
        LocalDateTime fimDaSemana = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)).toLocalDate().atTime(23, 59);

        BigDecimal somaValorSaidas = calcularSomaValorPorTipoEData("S", inicioDaSemana, fimDaSemana);
        somaValorSaidas = somaValorSaidas != null ? somaValorSaidas : BigDecimal.ZERO;

        Integer somaQuantidadeSaidas = calcularSomaQuantidadePorTipoEData("S", inicioDaSemana, fimDaSemana);
        somaQuantidadeSaidas = somaQuantidadeSaidas != null ? somaQuantidadeSaidas : 0;

        return new RelatorioSemanalSaidasDTO(somaQuantidadeSaidas, somaValorSaidas);
    }

    @Override
    public RelatorioSemanalSaldoDTO calcularRelatorioSemanalSaldo() {
        RelatorioSemanalEntradasDTO relatorioEntradas = calcularRelatorioSemanalEntradas();
        RelatorioSemanalSaidasDTO relatorioSaidas = calcularRelatorioSemanalSaidas();

        Integer saldoQuantidade = relatorioEntradas.getSomaQuantidadeEntradas() - relatorioSaidas.getSomaQuantidadeSaidas();
        BigDecimal saldoValor = relatorioEntradas.getSomaValorEntradas().subtract(relatorioSaidas.getSomaValorSaidas());

        return new RelatorioSemanalSaldoDTO(saldoQuantidade, saldoValor);
    }


}
