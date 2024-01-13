package br.unisc.biblioteca.Produto.Persistence;

import br.unisc.biblioteca.Produto.DTOs.ProdutoDto;
import br.unisc.biblioteca.Produto.Banco.ProdutoEntity;
import br.unisc.biblioteca.Produto.Repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class ProdutoPersistenceAdapter implements ProdutoPersistence {

    private final ProdutoRepository repository;

    @Override
    public void criarProduto(ProdutoDto produtoDto) {
        repository.findById(produtoDto.getId());
        var produtoEntidade = ProdutoEntity.criarEntidade(produtoDto);
        repository.save(produtoEntidade);
    }

    @Override
    public void updateProduto(Long id, ProdutoDto produtoDto) {
        var entidadeOptional = repository.findById(id);
        var entidade = entidadeOptional.get();
        entidade.setNome(StringUtils.isBlank(produtoDto.getNome()) ? entidade.getNome() : produtoDto.getNome());
        entidade.setMarca(StringUtils.isBlank(produtoDto.getMarca()) ? entidade.getMarca() : produtoDto.getMarca());
        entidade.setPeso(StringUtils.isBlank(produtoDto.getPeso()) ? entidade.getPeso() : produtoDto.getPeso());
        entidade.setPreco_custo(produtoDto.getPreco_custo());
        entidade.setPreco_venda(produtoDto.getPreco_venda());

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