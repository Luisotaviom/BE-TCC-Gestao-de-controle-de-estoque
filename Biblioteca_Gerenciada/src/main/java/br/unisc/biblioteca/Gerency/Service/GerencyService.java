package br.unisc.biblioteca.BiblioGerency.Service;

import br.unisc.biblioteca.BiblioGerency.DTOs.GerencyDTO2;
import br.unisc.biblioteca.BiblioGerency.Persistence.GerencyPersistence;
import br.unisc.biblioteca.BiblioGerency.DTOs.GerencyDTO;
import br.unisc.biblioteca.BiblioGerency.DTOs.ProdutoEncontradoFornecedorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GerencyService {

    private final GerencyPersistence persistence;

    public void addProdutoNoFornecedor(GerencyDTO dto) {
        persistence.addProdutoNoFornecedor(dto);
    }

    public void deleteProdutoDoFornecedor(Long bibliotecaId, Long livroId) {
        persistence.deleteProdutoDoFornecedor(bibliotecaId, livroId);
    }

    public List<ProdutoEncontradoFornecedorDTO> produtoPorNome(String titulo) {
        return persistence.produtoPorNome(titulo);
    }

    public Page<ProdutoEncontradoFornecedorDTO> ProdutosDoFornecedor(Long bibliotecaId, Pageable pageable) {
        return persistence.ProdutosDoFornecedor(bibliotecaId, pageable);
    }

    public Page<GerencyDTO2> buscarVinculados(Pageable pageable) {
        return persistence.buscarVinculados(pageable);
    }
}