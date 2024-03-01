package br.unisc.biblioteca.Movimentacoes.Service;

import br.unisc.biblioteca.Movimentacoes.Banco.MovimentacaoEntity;
import br.unisc.biblioteca.Movimentacoes.DTOs.*;
import br.unisc.biblioteca.Movimentacoes.Persistence.Exceptions.ValidacaoNegocioException;
import br.unisc.biblioteca.Movimentacoes.Persistence.MovimentacaoPersistenceAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MovimentacaoService {

    private final MovimentacaoPersistenceAdapter movimentacaoPersistenceAdapter;

    public ResponseEntity<Object> criarMovimentacao(MovimentacaoDTO movimentacaoDto) {
        try {
            movimentacaoPersistenceAdapter.criarMovimentacao(movimentacaoDto);
            return ResponseEntity.ok("Movimentação criada com sucesso");
        } catch (ValidacaoNegocioException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    public void updateMovimentacao(Long id, MovimentacaoDTO movimentacaoDTO) {
        movimentacaoPersistenceAdapter.updateMovimentacao(id, movimentacaoDTO);
    }

    public void deleteMovimentacao(Long id) {
        movimentacaoPersistenceAdapter.deleteMovimentacao(id);
    }

    public MovimentacaoDTO buscarPorId(Long id) {
        return movimentacaoPersistenceAdapter.buscarPorId(id);
    }

    public Page<MovimentacaoDetalhesDTO> buscarTodasMovimentacoes(Pageable pageable) {
        return movimentacaoPersistenceAdapter.buscarTodasMovimentacoes(pageable);
    }

    public Page<MovimentacaoDetalhesDTO> buscarPorTipoECategoriaEData(String tipo, String categoria, Pageable pageable) {

        return movimentacaoPersistenceAdapter.buscarPorTipoECategoria(tipo, categoria, pageable);
    }


    public Page<MovimentacaoDetalhesDTO> buscaNomeETipoECategoriaEData(String nome, String tipo, String categoria, LocalDateTime start, LocalDateTime end, Pageable pageable) {

        return movimentacaoPersistenceAdapter.buscaNomeETipoECategoriaEData(nome, tipo, categoria, start, end, pageable);
    }


}