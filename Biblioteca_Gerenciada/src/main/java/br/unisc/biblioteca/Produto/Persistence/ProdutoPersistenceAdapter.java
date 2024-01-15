package br.unisc.biblioteca.Produto.Persistence;

<<<<<<< HEAD
=======
import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import br.unisc.biblioteca.Fornecedor.Repository.FornecedorRepository;
>>>>>>> 7de9f890f1179eadc16c3425d0b53cdaef6c2d36
import br.unisc.biblioteca.Produto.DTOs.ProdutoDto;
import br.unisc.biblioteca.Produto.Banco.ProdutoEntity;
import br.unisc.biblioteca.Produto.Repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class ProdutoPersistenceAdapter implements ProdutoPersistence {

    private final ProdutoRepository repository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Override
    public void criarProduto(Long id, ProdutoDto produtoDto) {
        Optional<FornecedorEntity> fornecedorOptional = fornecedorRepository.findById(id);
        FornecedorEntity fornecedor = fornecedorOptional.get();
        var produtoEntidade = ProdutoEntity.criarEntidade(produtoDto);
        produtoEntidade.setFornecedor_id(fornecedor.getId());

        repository.save(produtoEntidade);
    }


    @Override
    public void updateProduto(Long id, ProdutoDto produtoDto) {
        var entidadeOptional = repository.findById(id);
        var entidade = entidadeOptional.get();
        entidade.setNome(StringUtils.isBlank(produtoDto.getNome()) ? entidade.getNome() : produtoDto.getNome());
        entidade.setFornecedor_id(produtoDto.getFornecedor_id());
        entidade.setCategoria(StringUtils.isBlank(produtoDto.getCategoria()) ? entidade.getCategoria() : produtoDto.getCategoria());

        repository.save(entidade);
    }

    @Override
    public void deleteProduto(Long id) {
        repository.findById(id);
        repository.deleteById(id);
    }

    @Override
    public Page<ProdutoDto> buscarTodosProduto(Pageable pageable) {
        return repository.findAll(pageable)
                .map(ProdutoEntity::converterEntidadeParaDto);
    }

    @Override
    public Object buscarPorId(Long id) {
        Optional<ProdutoEntity> produtoOptional = repository.findById(id);

        if (produtoOptional.isPresent()) {
            ProdutoEntity produto = produtoOptional.get();
            return ProdutoEntity.converterEntidadeParaDto(produto);
        } else {
            return null;
        }
    }

}