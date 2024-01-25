package br.unisc.biblioteca.Produto.Controller;

import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import br.unisc.biblioteca.Fornecedor.DTOs.FornecedorDTO;
import br.unisc.biblioteca.Produto.Banco.ProdutoEntity;
import br.unisc.biblioteca.Produto.DTOs.ProdutoDto;
import br.unisc.biblioteca.Produto.DTOs.ProdutosDoFornecedorDto;
import br.unisc.biblioteca.Produto.Service.ProdutoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping("/fornecedor/{id}/produto")
    public ResponseEntity<?> criarProduto(@PathVariable Long id, @RequestBody ProdutoDto produtoDto) {
        produtoService.criarProduto(id, produtoDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> buscarPorId(@PathVariable Long id) {
        var produtos = produtoService.buscarPorId(id);
        return ResponseEntity.ok((ProdutoDto) produtos);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduto(@RequestBody ProdutoDto produtoDto, @PathVariable Long id) {
        try {
            produtoService.updateProduto(id, produtoDto);
            return ResponseEntity.ok().build(); // ResponseEntity.ok() já retorna o status 200
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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

    @GetMapping("/fornecedor/{fornecedor_id}")
    public ResponseEntity<Page<ProdutosDoFornecedorDto>> buscarProdutosPorFornecedor(@PathVariable Long fornecedor_id, Pageable pageable) {
        Page<ProdutosDoFornecedorDto> produtos = produtoService.buscarProdutosDoFornecedor(fornecedor_id, pageable);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/buscarPorStatus")
    public ResponseEntity<Page<ProdutoDto>> listProdutosPorStatus(
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ProdutoEntity> produtoEntities;

        if (ativo != null) {
            produtoEntities = produtoService.buscarProdutosPorStatus(ativo, pageable);
        } else {
            produtoEntities = produtoService.buscarTodos(pageable);
        }

        Page<ProdutoDto> produtosDTOs = produtoEntities.map(ProdutoEntity::converterEntidadeParaDto);

        return ResponseEntity.ok(produtosDTOs);
    }

    @GetMapping("/produtosPorNome")
    public ResponseEntity<Page<ProdutoDto>> buscarPorNome(
            @RequestParam(value = "nome", required = false) String nome,
            Pageable pageable) {
        Page<ProdutoEntity> entityPage = produtoService.buscarPorNome(nome, pageable);
        Page<ProdutoDto> dtoPage = entityPage.map(ProdutoEntity::converterEntidadeParaDto);
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }



}