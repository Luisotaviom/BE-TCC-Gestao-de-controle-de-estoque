package br.unisc.biblioteca.Movimentacoes.Controller;

import br.unisc.biblioteca.Movimentacoes.DTOs.MovimentacaoDTO;
import br.unisc.biblioteca.Movimentacoes.Service.MovimentacaoService;
import br.unisc.biblioteca.Produto.DTOs.ProdutoDto;
import br.unisc.biblioteca.Produto.DTOs.ProdutosDoFornecedorDto;
import br.unisc.biblioteca.Produto.Service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    @PostMapping
    public ResponseEntity<?> criarMovimentacao(@RequestBody MovimentacaoDTO movimentacaoDTO) {
        movimentacaoService.criarMovimentacao(movimentacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<MovimentacaoDTO>> buscarTodasMovimentacoes(Pageable pageable) {
        return ResponseEntity.ok(movimentacaoService.buscarTodasMovimentacoes(pageable));
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


}