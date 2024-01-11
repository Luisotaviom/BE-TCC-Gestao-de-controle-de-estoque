package br.unisc.biblioteca.BiblioGerency.Persistence;

import br.unisc.biblioteca.BiblioGerency.Banco.GerenciaEntity;
import br.unisc.biblioteca.BiblioGerency.DTOs.GerencyDTO2;
import br.unisc.biblioteca.BiblioGerency.DTOs.ProdutoEncontradoFornecedorDTO;
import br.unisc.biblioteca.BiblioGerency.Repository.GerencyRepository;
import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import br.unisc.biblioteca.BiblioGerency.DTOs.GerencyDTO;
import br.unisc.biblioteca.Fornecedor.Repository.FornecedorRepository;
import br.unisc.biblioteca.Livro.Banco.ProdutoEntity;
import br.unisc.biblioteca.Livro.Repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GerencyPersistenceAdapter implements GerencyPersistence {

    private final FornecedorRepository fornecedorRepository;
    private final ProdutoRepository produtoRepository;
    private final GerencyRepository gerencyRepository;

    @Override
    public void addProdutoNoFornecedor(GerencyDTO dto) {
        FornecedorEntity fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                .orElseThrow();

        ProdutoEntity produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow();

        GerenciaEntity gerency = new GerenciaEntity();
        gerency.setFornecedor(fornecedor);
        gerency.setProduto(produto);
        gerencyRepository.save(gerency);
    }

        @Override
        @Transactional
        public void deleteProdutoDoFornecedor(Long fornecedorId, Long produtoId) {
            gerencyRepository.existsByBibliotecaIdAndLivroId(fornecedorId, produtoId);
            gerencyRepository.deleteByFornecedorIdAndProdutoId(fornecedorId, produtoId);
        }

    @Override
    public List<ProdutoEncontradoFornecedorDTO> produtoPorNome(String nome) {
        return gerencyRepository.findAllByProdutoNomeContaining(nome)
                .stream()
                .map(GerenciaEntity::convertEntityToDTO)
                .toList();
    }

    @Override
    public Page<ProdutoEncontradoFornecedorDTO> ProdutosDoFornecedor(Long fornecedorId, Pageable pageable) {
        var entidadeOptional = gerencyRepository.findByFornecedorId(fornecedorId, pageable);
        entidadeOptional.isEmpty();
        Page<GerenciaEntity> bibliotecaLivros = gerencyRepository.findByFornecedorId(fornecedorId, pageable);
        return bibliotecaLivros.map(GerenciaEntity::convertEntityToDTO);
    }

    @Override
    public Page<GerencyDTO2> buscarVinculados(Pageable pageable) {
        return gerencyRepository.findAll(pageable)
                .map(GerenciaEntity::convertEntidadeParaDTO);
    }
}