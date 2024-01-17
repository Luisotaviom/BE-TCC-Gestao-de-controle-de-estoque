package br.unisc.biblioteca.Movimentacoes.Service;

import br.unisc.biblioteca.Movimentacoes.DTOs.MovimentacaoDTO;
import br.unisc.biblioteca.Movimentacoes.Persistence.MovimentacaoPersistenceAdapter;
import br.unisc.biblioteca.Produto.DTOs.ProdutoDto;
import br.unisc.biblioteca.Produto.DTOs.ProdutosDoFornecedorDto;
import br.unisc.biblioteca.Produto.Persistence.ProdutoPersistence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Slf4j
@Service
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MovimentacaoService {

    private final MovimentacaoPersistenceAdapter movimentacaoPersistenceAdapter;

    public void criarMovimentacao(MovimentacaoDTO movimentacaoDTO) {movimentacaoPersistenceAdapter.criarMovimentacao(movimentacaoDTO); }

    public void updateMovimentacao(Long id, MovimentacaoDTO movimentacaoDTO) {
        movimentacaoPersistenceAdapter.updateMovimentacao(id, movimentacaoDTO);
    }

    public void deleteMovimentacao(Long id) {
        movimentacaoPersistenceAdapter.deleteMovimentacao(id);
    }

    public MovimentacaoDTO buscarPorId(Long id) {
        return movimentacaoPersistenceAdapter.buscarPorId(id);
    }

    public Page<MovimentacaoDTO> buscarTodasMovimentacoes(Pageable pageable) {
        return movimentacaoPersistenceAdapter.buscarTodasMovimentacoes(pageable);
    }


}