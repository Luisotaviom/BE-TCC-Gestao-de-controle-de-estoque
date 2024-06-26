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

    @Override
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
    public Page<MovimentacaoDetalhesDTO> buscaNomeETipoECategoriaEData(String nome, String tipo, String categoria, LocalDateTime start, LocalDateTime end, Pageable pageable) {

        return movimentacaoRepository.buscaNomeETipoECategoriaEData(
                nome,
                tipo,
                categoria,
                start,
                end,
                pageable).map(MovimentacaoEntity::convertEntidadeParaDto);
    }

    @Override
    public Page<MovimentacaoDetalhesDTO> buscarPorTipoECategoria(String tipo, String categoria, Pageable pageable) {
        Page<MovimentacaoEntity> page = movimentacaoRepository.findByTipoAndCategoria(tipo, categoria, pageable);
        return page.map(MovimentacaoEntity::convertEntidadeParaDto);
    }




}
