package br.unisc.biblioteca.Produto_1.Controller;

import br.unisc.biblioteca.Produto_1.DTOs.ProdutoDto;
import br.unisc.biblioteca.Produto_1.Service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<?> criarProduto(@RequestBody ProdutoDto produtoDto) {
        produtoService.criarProduto(produtoDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> buscarPorId(@PathVariable Long id) {
        var produtos = produtoService.buscarPorId(id);
        return ResponseEntity.ok((ProdutoDto) produtos);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduto(@RequestBody ProdutoDto produtoDto, @PathVariable Long id) {
        produtoService.updateProduto(id, produtoDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> deleteProduto(@PathVariable String id) {
        try {
            if (id != null && !id.trim().isEmpty()) {
                Long prdutoId = Long.parseLong(id);
                produtoService.deleteProduto(prdutoId);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping
    public ResponseEntity<Page<ProdutoDto>> buscarTodosProdutos(Pageable pageable) {
        return ResponseEntity.ok(produtoService.buscarTodosProdutos(pageable));
    }
}