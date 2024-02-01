package br.unisc.biblioteca.Movimentacoes.Controller;

import br.unisc.biblioteca.Movimentacoes.DTOs.*;
import br.unisc.biblioteca.Movimentacoes.Service.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    private static final Logger logger = LoggerFactory.getLogger(MovimentacaoController.class);


    @PostMapping
    public ResponseEntity<Object> criarMovimentacao(@RequestBody MovimentacaoDTO movimentacaoDto) {
        ResponseEntity<Object> responseEntity = movimentacaoService.criarMovimentacao(movimentacaoDto);
        return responseEntity;
    }

    @GetMapping
    public ResponseEntity<Page<MovimentacaoDetalhesDTO>> buscarTodasMovimentacoes(Pageable pageable) {
        Page<MovimentacaoDetalhesDTO> page = movimentacaoService.buscarTodasMovimentacoes(pageable);
        return ResponseEntity.ok(page);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovimentacao(@RequestBody MovimentacaoDTO movimentacaoDTO, @PathVariable Long id) {
        movimentacaoService.updateMovimentacao(id, movimentacaoDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> deleteMovimentacao(@PathVariable String id) {
        try {
            if (id != null && !id.trim().isEmpty()) {
                Long MovId = Long.parseLong(id);
                movimentacaoService.deleteMovimentacao(MovId);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoDTO> buscarPorId(@PathVariable Long id) {
        var movimentacoes = movimentacaoService.buscarPorId(id);
        return ResponseEntity.ok((MovimentacaoDTO) movimentacoes);
    }

    @GetMapping("/buscarPorTipo")
    public ResponseEntity<Page<MovimentacaoDTO>> listMovimentacoesPorTipo(
            @RequestParam(required = false) String tipo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<MovimentacaoDTO> movimentacoes = movimentacaoService.buscarPorTipo(tipo, pageable);

        return ResponseEntity.ok(movimentacoes);
    }

    @GetMapping("/buscarPorData")
    public ResponseEntity<Page<MovimentacaoDTO>> buscarPorData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            Pageable pageable) {

        Page<MovimentacaoDTO> movimentacoes = movimentacaoService.buscarPorIntervaloDeData(start, end, pageable);
        return ResponseEntity.ok(movimentacoes);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<MovimentacaoDTO>> buscarMovimentacoes(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            Pageable pageable) {

        Page<MovimentacaoDTO> movimentacoes = movimentacaoService.buscarPorTipoEData(tipo, start, end, pageable);
        return ResponseEntity.ok(movimentacoes);
    }

    @GetMapping("/relatorio/semanal")
    public ResponseEntity<Page<MovimentacaoDTO>> buscarMovimentacoesSemanais(
            @RequestParam(required = false) String tipo,
            Pageable pageable) {
        LocalDateTime hoje = LocalDateTime.now();
        LocalDateTime start = hoje.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDateTime end = hoje.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        Page<MovimentacaoDTO> page;
        if ("E".equals(tipo) || "S".equals(tipo)) {
            page = movimentacaoService.buscarPorTipoEData(tipo, start, end, pageable);
        } else {
            page = movimentacaoService.buscarPorIntervaloDeData(start, end, pageable);
        }

        return ResponseEntity.ok(page);
    }

    @GetMapping("/relatorio/mensal")
    public ResponseEntity<Page<MovimentacaoDTO>> buscarMovimentacoesMensais(
            @RequestParam(required = false) String tipo,
            Pageable pageable) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioDoMes = agora.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate().atStartOfDay();
        LocalDateTime fimDoMes = agora.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate().atTime(23, 59);

        Page<MovimentacaoDTO> page;
        if ("E".equals(tipo) || "S".equals(tipo)) {
            page = movimentacaoService.buscarPorTipoEData(tipo, inicioDoMes, fimDoMes, pageable);
        } else {
            page = movimentacaoService.buscarPorIntervaloDeData(inicioDoMes, fimDoMes, pageable);
        }

        return ResponseEntity.ok(page);
    }


    @GetMapping("/relatorio-semanal")
    public ResponseEntity<?> calcularRelatorioSemanal(@RequestParam(required = false) String tipo) {
        if ("E".equals(tipo)) {
            RelatorioSemanalEntradasDTO relatorioEntradas = movimentacaoService.calcularRelatorioSemanalEntradas();
            return ResponseEntity.ok(relatorioEntradas);
        } else if ("S".equals(tipo)) {
            RelatorioSemanalSaidasDTO relatorioSaidas = movimentacaoService.calcularRelatorioSemanalSaidas();
            return ResponseEntity.ok(relatorioSaidas);
        } else {
            RelatorioSemanalSaldoDTO relatorioSaldo = movimentacaoService.calcularRelatorioSemanalSaldo();
            return ResponseEntity.ok(relatorioSaldo);
        }
    }

}