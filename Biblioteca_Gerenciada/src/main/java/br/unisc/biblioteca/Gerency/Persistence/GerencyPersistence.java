package br.unisc.biblioteca.Gerency.Persistence;

import br.unisc.biblioteca.Gerency.DTOs.GerencyDTO2;
import br.unisc.biblioteca.Gerency.DTOs.ProdutoEncontradoFornecedorDTO;
import br.unisc.biblioteca.Gerency.DTOs.GerencyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GerencyPersistence {

    void deleteProdutoDoFornecedor(Long fornecedorId, Long nomeId);

    List<ProdutoEncontradoFornecedorDTO> produtoPorNome(String nome);

    Page<ProdutoEncontradoFornecedorDTO> ProdutosDoFornecedor(Long fornecedorId, Pageable pageable);

    Page<GerencyDTO2> buscarVinculados(Pageable pageable);
}
