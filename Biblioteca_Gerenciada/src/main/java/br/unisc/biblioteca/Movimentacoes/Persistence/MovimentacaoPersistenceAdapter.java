package br.unisc.biblioteca.Movimentacoes.Persistence;

import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import br.unisc.biblioteca.Fornecedor.Repository.FornecedorRepository;
import br.unisc.biblioteca.Movimentacoes.Banco.MovimentacaoEntity;
import br.unisc.biblioteca.Movimentacoes.DTOs.MovimentacaoDTO;
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
    public void criarMovimentacao(MovimentacaoDTO movimentacaoDto) {
        // Busca o fornecedor pelo ID fornecido no DTO
        Optional<FornecedorEntity> fornecedorOptional = fornecedorRepository.findById(movimentacaoDto.getFornecedor_id());
        FornecedorEntity fornecedor = fornecedorOptional.orElse(null);

        // Busca o produto pelo ID fornecido no DTO
        Optional<ProdutoEntity> produtoOptional = produtoRepository.findById(movimentacaoDto.getProduto_id());
        ProdutoEntity produto = produtoOptional.orElse(null);

        // Cria a entidade de movimentação
        MovimentacaoEntity movimentacaoEntidade = MovimentacaoEntity.criarEntidade(movimentacaoDto, fornecedor, produto);

        // Salva a entidade de movimentação
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
            return null; // Ou você pode optar por lançar uma exceção se a movimentação não for encontrada.
        }
    }

    @Override
    public Page<MovimentacaoDTO> buscarTodasMovimentacoes(Pageable pageable) {
        return movimentacaoRepository.findAll(pageable)
                .map(MovimentacaoEntity::convertEntidadeParaDto);
    }
}