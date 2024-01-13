package br.unisc.biblioteca.Gerency.Controller;

import br.unisc.biblioteca.Gerency.DTOs.GerencyDTO2;
import br.unisc.biblioteca.Gerency.DTOs.ProdutoEncontradoFornecedorDTO;
import br.unisc.biblioteca.Gerency.Service.GerencyService;
import br.unisc.biblioteca.Gerency.DTOs.GerencyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Gerency")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GerencyController {

    private final GerencyService gerenciarService;


    @PostMapping("/addLivroNaBiblioteca")
    public ResponseEntity<?> addProdutoNoFornecedor(@RequestBody GerencyDTO dto) {
        gerenciarService.addProdutoNoFornecedor(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{FornecedorId}/Livros/{ProdutoId}")
    public ResponseEntity<?> deleteProdutoDoFornecedor(@PathVariable Long FornecedorId, @PathVariable Long ProdutoId){
        gerenciarService.deleteProdutoDoFornecedor(FornecedorId, ProdutoId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/produtoPorNome")
    public ResponseEntity<List<ProdutoEncontradoFornecedorDTO>> produtoPorNome(@RequestParam("nome") String nome) {
        return ResponseEntity.ok(gerenciarService.produtoPorNome(nome));
    }


    @GetMapping("/ProdutosDoFornecedor/{id}")
    public ResponseEntity<Page<ProdutoEncontradoFornecedorDTO>> ProdutosDoFornecedor(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity
                .ok(gerenciarService.ProdutosDoFornecedor(id, pageable));
    }

    @GetMapping
    public ResponseEntity<Page<GerencyDTO2>> buscarVinculados(Pageable pageable) {
        return ResponseEntity
                .ok(gerenciarService.buscarVinculados(pageable));
    }

}