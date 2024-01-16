package br.unisc.biblioteca.Produto.Persistence;

import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import br.unisc.biblioteca.Fornecedor.Repository.FornecedorRepository;
import br.unisc.biblioteca.Produto.DTOs.ProdutoDto;
import br.unisc.biblioteca.Produto.Banco.ProdutoEntity;
import br.unisc.biblioteca.Produto.DTOs.ProdutosDoFornecedorDto;
import br.unisc.biblioteca.Produto.Repository.ProdutoRepository;
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
    public void criarProduto(Long id, ProdutoDto produtoDto) {
        Optional<FornecedorEntity> fornecedorOptional = fornecedorRepository.findById(id);
        FornecedorEntity fornecedor = fornecedorOptional.get();
        var produtoEntidade = ProdutoEntity.criarEntidade(produtoDto);
        produtoEntidade.setFornecedor_id(fornecedor.getId());

        produtoRepository.save(produtoEntidade);
    }


    @Override
    public void updateProduto(Long id, ProdutoDto produtoDto) {
        var entidadeOptional = produtoRepository.findById(id);
        var entidade = entidadeOptional.get();
        entidade.setNome(StringUtils.isBlank(produtoDto.getNome()) ? entidade.getNome() : produtoDto.getNome());
        entidade.setFornecedor_id(produtoDto.getFornecedor_id());
        entidade.setCategoria(StringUtils.isBlank(produtoDto.getCategoria()) ? entidade.getCategoria() : produtoDto.getCategoria());

        produtoRepository.save(entidade);
    }

    @Override
    public void deleteProduto(Long id) {
        produtoRepository.findById(id);
        produtoRepository.deleteById(id);
    }

    @Override
    public Page<ProdutoDto> buscarTodosProduto(Pageable pageable) {
        return produtoRepository.findAll(pageable)
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
}