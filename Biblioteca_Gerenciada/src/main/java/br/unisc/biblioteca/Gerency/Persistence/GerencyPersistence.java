package br.unisc.biblioteca.Gerency.Persistence;

import br.unisc.biblioteca.Gerency.DTOs.GerencyDTO2;
import br.unisc.biblioteca.Gerency.DTOs.ProdutoEncontradoFornecedorDTO;
import br.unisc.biblioteca.Gerency.DTOs.GerencyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GerencyPersistence {

    void addProdutoNoFornecedor(GerencyDTO dto);

    void deleteProdutoDoFornecedor(Long bibliotecaId, Long livroId);

    List<ProdutoEncontradoFornecedorDTO> produtoPorNome(String titulo);

    Page<ProdutoEncontradoFornecedorDTO> ProdutosDoFornecedor(Long bibliotecaId, Pageable pageable);

    Page<GerencyDTO2> buscarVinculados(Pageable pageable);
}
