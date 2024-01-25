package br.unisc.biblioteca.Movimentacoes.Persistence;

import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import br.unisc.biblioteca.Fornecedor.Repository.FornecedorRepository;
import br.unisc.biblioteca.Movimentacoes.Banco.MovimentacaoEntity;
import br.unisc.biblioteca.Movimentacoes.Controller.MovimentacaoController;
import br.unisc.biblioteca.Movimentacoes.DTOs.MovimentacaoDTO;
import br.unisc.biblioteca.Movimentacoes.DTOs.MovimentacaoDetalhesDTO;
import br.unisc.biblioteca.Movimentacoes.DTOs.SomaInfo;
import br.unisc.biblioteca.Movimentacoes.Persistence.Exceptions.EntidadeNaoEncontradaException;
import br.unisc.biblioteca.Movimentacoes.Persistence.Exceptions.ValidacaoNegocioException;
import br.unisc.biblioteca.Movimentacoes.Repository.MovimentacaoRepository;
import br.unisc.biblioteca.Produto.Banco.ProdutoEntity;
import br.unisc.biblioteca.Produto.Repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    private static final Logger logger = LoggerFactory.getLogger(MovimentacaoPersistenceAdapter.class);



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
                .map(MovimentacaoEntity::convertEntidadeParaDto); // Método atualizado para converter para o DTO detalhado
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
    public Page<MovimentacaoDetalhesDTO> gerarRelatorioSemanal(String tipo, Pageable pageable) {
        logger.info("Chamada a gerarRelatorioSemanal com tipo: {}", tipo);

        LocalDate hoje = LocalDate.now();
        LocalDate inicioSemana = hoje.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate fimSemana = hoje.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        LocalDateTime start = inicioSemana.atStartOfDay();
        LocalDateTime end = fimSemana.atTime(LocalTime.MAX);

        // Busque os dados do relatório
        Page<MovimentacaoDetalhesDTO> relatorio = buscarRelatorioPorTipoEData(tipo, start, end, pageable);

        // Calcule as somas dos valores e quantidades
        BigDecimal somaValores = calcularSomaValorPorTipoEData(tipo, start, end);
        Integer somaQuantidades = calcularSomaQuantidadePorTipoEData(tipo, start, end);

        // Defina as somas no DTO de resposta
        SomaInfo relatorioSoma = new SomaInfo();
        relatorioSoma.setSomaValores(somaValores);
        relatorioSoma.setSomaQuantidades(somaQuantidades);

        relatorio.getContent().forEach(dto -> dto.setSomaInfo(relatorioSoma));

        return relatorio;
    }

    @Override
    public Page<MovimentacaoDetalhesDTO> gerarRelatorioMensal(String tipo, Pageable pageable) {
        logger.info("Chamada a gerarRelatorioMensal com tipo: {}", tipo);

        LocalDate hoje = LocalDate.now();
        LocalDate inicioMes = hoje.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate fimMes = hoje.with(TemporalAdjusters.lastDayOfMonth());
        LocalDateTime start = inicioMes.atStartOfDay();
        LocalDateTime end = fimMes.atTime(LocalTime.MAX);

        // Busque os dados do relatório
        Page<MovimentacaoDetalhesDTO> relatorio = buscarRelatorioPorTipoEData(tipo, start, end, pageable);

        // Calcule as somas dos valores e quantidades
        BigDecimal somaValores = calcularSomaValorPorTipoEData(tipo, start, end);
        Integer somaQuantidades = calcularSomaQuantidadePorTipoEData(tipo, start, end);

        // Defina as somas no DTO de resposta
        SomaInfo relatorioSoma = new SomaInfo();
        relatorioSoma.setSomaValores(somaValores);
        relatorioSoma.setSomaQuantidades(somaQuantidades);

        relatorio.getContent().forEach(dto -> dto.setSomaInfo(relatorioSoma));

        return relatorio;
    }


    private Page<MovimentacaoDetalhesDTO> buscarRelatorioPorTipoEData(String tipo, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        BigDecimal somaValores = movimentacaoRepository.calcularSomaValorPorTipoEData(tipo, start, end);
        Integer somaQuantidades = movimentacaoRepository.calcularSomaQuantidadePorTipoEData(tipo, start, end);

        Page<MovimentacaoEntity> movimentacoesPage = movimentacaoRepository.findByTipoAndDataRegistroBetween(tipo, start, end, pageable);
        return movimentacoesPage.map(entidade -> {
            MovimentacaoDetalhesDTO dto = MovimentacaoEntity.convertEntidadeParaDto(entidade);

            // Criar um objeto temporário com as somas
            SomaInfo somaInfo = new SomaInfo();
            somaInfo.setSomaValores(somaValores);
            somaInfo.setSomaQuantidades(somaQuantidades);

            // Definir o objeto de soma no DTO
            dto.setSomaInfo(somaInfo);

            return dto;
        });
    }

    @Override
    public BigDecimal calcularSomaValorPorTipoEData(String tipo, LocalDateTime start, LocalDateTime end) {
        return movimentacaoRepository.calcularSomaValorPorTipoEData(tipo, start, end);
    }

    @Override
    public Integer calcularSomaQuantidadePorTipoEData(String tipo, LocalDateTime start, LocalDateTime end) {
        return movimentacaoRepository.calcularSomaQuantidadePorTipoEData(tipo, start, end);
    }




}
