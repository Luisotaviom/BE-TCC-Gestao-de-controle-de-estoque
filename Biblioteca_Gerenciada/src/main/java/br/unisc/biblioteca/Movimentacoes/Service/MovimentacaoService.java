package br.unisc.biblioteca.Movimentacoes.Service;

import br.unisc.biblioteca.Movimentacoes.Banco.MovimentacaoEntity;
import br.unisc.biblioteca.Movimentacoes.DTOs.MovimentacaoDTO;
import br.unisc.biblioteca.Movimentacoes.DTOs.MovimentacaoDetalhesDTO;
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

import java.time.LocalDate;
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

    public Page<MovimentacaoDTO> buscarPorTipo(String tipo, Pageable pageable) {
        Page<MovimentacaoEntity> entidades = movimentacaoPersistenceAdapter.buscarPorTipo(tipo, pageable);

        return entidades.map(MovimentacaoEntity::convertEntidadeParaDto);
    }


    public Page<MovimentacaoDTO> buscarPorIntervaloDeData(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return movimentacaoPersistenceAdapter.buscarPorIntervaloDeData(start, end, pageable);
    }

    public Page<MovimentacaoDTO> buscarPorTipoEData(String tipo, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return movimentacaoPersistenceAdapter.buscarPorTipoEData(tipo, start, end, pageable);
    }

}