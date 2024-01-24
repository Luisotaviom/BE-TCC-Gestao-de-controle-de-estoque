package br.unisc.biblioteca.Produto.Persistence;

import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import br.unisc.biblioteca.Fornecedor.Repository.FornecedorRepository;
import br.unisc.biblioteca.Movimentacoes.Persistence.Exceptions.EntidadeNaoEncontradaException;
import br.unisc.biblioteca.Produto.DTOs.ProdutoDto;
import br.unisc.biblioteca.Produto.Banco.ProdutoEntity;
import br.unisc.biblioteca.Produto.DTOs.ProdutosDoFornecedorDto;
import br.unisc.biblioteca.Produto.Repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ProdutoPersistenceAdapter implements ProdutoPersistence {

    @Autowired
    private final ProdutoRepository produtoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Override
    public void criarProduto(Long fornecedorId, ProdutoDto produtoDto) {
        FornecedorEntity fornecedor = fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Fornecedor não encontrado com ID: " + fornecedorId));

        ProdutoEntity produtoEntidade = ProdutoEntity.builder()
                .nome(produtoDto.getNome())
                .fornecedor(fornecedor)
                .categoria(produtoDto.getCategoria())
                .ativo(produtoDto.getAtivo())
                .build();

        produtoRepository.save(produtoEntidade);
    }



    @Override
    public void updateProduto(Long id, ProdutoDto produtoDto) {
        ProdutoEntity entidade = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));

        entidade.setNome(StringUtils.isBlank(produtoDto.getNome()) ? entidade.getNome() : produtoDto.getNome());
        entidade.setCategoria(StringUtils.isBlank(produtoDto.getCategoria()) ? entidade.getCategoria() : produtoDto.getCategoria());

        if (produtoDto.getFornecedor_id() != null && !produtoDto.getFornecedor_id().equals(entidade.getFornecedor().getId())) {
            FornecedorEntity fornecedor = fornecedorRepository.findById(produtoDto.getFornecedor_id())
                    .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com ID: " + produtoDto.getFornecedor_id()));
            entidade.setFornecedor(fornecedor);
        }

        if (produtoDto.getAtivo() != null) {
            entidade.setAtivo(produtoDto.getAtivo());
        }
        produtoRepository.save(entidade);
    }


    @Override
    public void deleteProduto(Long id) {
        produtoRepository.findById(id);
        produtoRepository.deleteById(id);
    }

    @Override
    public Page<ProdutoDto> buscarTodosProduto(Pageable pageable) {
        return produtoRepository.findAllWithFornecedor(pageable)
                .map(ProdutoEntity::converterEntidadeParaDto);
    }

    @Override
    public Object buscarPorId(Long id) {
        Optional<ProdutoEntity> produtoOptional = produtoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            ProdutoEntity produto = produtoOptional.get();
            return ProdutoEntity.converterEntidadeParaDto(produto);
        } else {
            return null;
        }
    }

    @Override
    public Page<ProdutosDoFornecedorDto> buscarProdutosDoFornecedor(Long fornecedorId, Pageable pageable) {
        Page<ProdutoEntity> produtosDoFornecedor = produtoRepository.findByFornecedorId(fornecedorId, pageable);

        Page<ProdutosDoFornecedorDto> produtosDto = produtosDoFornecedor.map(produto -> new ProdutosDoFornecedorDto(
                produto.getId(),
                produto.getNome(),
                produto.getCategoria()
        ));

        return produtosDto;
    }

    @Override
    public Page<ProdutoEntity> buscarTodos(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    @Override
    public Page<ProdutoEntity> buscarProdutosPorStatus(boolean ativo, Pageable pageable) {
        return produtoRepository.findByAtivo(ativo, pageable);
    }
}