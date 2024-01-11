package br.unisc.biblioteca.Biblioteca.Controller;

import br.unisc.biblioteca.BiblioGerency.Service.GerencyBiblioService;
import br.unisc.biblioteca.Biblioteca.DTOs.BibliotecaDTO;
import br.unisc.biblioteca.Biblioteca.Service.BibliotecaService;
import br.unisc.biblioteca.Livro.DTOs.LivroDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Bibliotecas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BibliotecaController {
    private final BibliotecaService bibliotecaService;

    @PostMapping
    public ResponseEntity<?> createBiblioteca(@RequestBody BibliotecaDTO bibliotecaDTO) {
        bibliotecaService.createBiblioteca(bibliotecaDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBiblioteca(@PathVariable Long id) {
        bibliotecaService.deleteBiblioteca(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBiblioteca(@RequestBody BibliotecaDTO bibliotecaDTO, @PathVariable Long id) {
        bibliotecaService.updateBiblioteca(id, bibliotecaDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping
    public ResponseEntity<Page<BibliotecaDTO>> buscarBiblioteca(Pageable pageable) {
        return ResponseEntity
                .ok(bibliotecaService.buscarBiblioteca(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BibliotecaDTO> buscarPorIdBiblio(@PathVariable Long id) {
        var biblioteca = bibliotecaService.buscarPorIdBiblio(id);
        return ResponseEntity.ok((BibliotecaDTO) biblioteca);
    }
}