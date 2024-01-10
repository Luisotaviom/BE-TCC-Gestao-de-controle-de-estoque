package br.unisc.biblioteca.Livro.Controller;

import br.unisc.biblioteca.Livro.DTOs.LivroDto;
import br.unisc.biblioteca.Livro.Service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livrosService;

    @PostMapping
    public ResponseEntity<?> criarLivro(@RequestBody LivroDto livroDto) {
        livrosService.criarLivro(livroDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDto> buscarPorId(@PathVariable Long id) {
        var livro = livrosService.buscarPorId(id);
        return ResponseEntity.ok((LivroDto) livro);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateLivro(@RequestBody LivroDto livroDto, @PathVariable Long id) {
        livrosService.updateLivro(id, livroDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> deleteLivro(@PathVariable String id) {
        try {
            if (id != null && !id.trim().isEmpty()) {
                Long livroId = Long.parseLong(id);
                livrosService.deleteLivro(livroId);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping
    public ResponseEntity<Page<LivroDto>> buscarTodosLivros(Pageable pageable) {
        return ResponseEntity.ok(livrosService.buscarTodosLivros(pageable));
    }
}