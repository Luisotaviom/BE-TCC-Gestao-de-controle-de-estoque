package br.unisc.biblioteca.BiblioGerency.Controller;

import br.unisc.biblioteca.BiblioGerency.DTOs.BiblioGerencyDTO2;
import br.unisc.biblioteca.BiblioGerency.DTOs.LivroEncontradoBibliotecaDTO;
import br.unisc.biblioteca.BiblioGerency.Service.GerencyBiblioService;
import br.unisc.biblioteca.BiblioGerency.DTOs.BiblioGerencyDTO;
import br.unisc.biblioteca.Biblioteca.DTOs.BibliotecaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/BibliotecaGerency")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BiblioGerencyController {

    private final GerencyBiblioService gerenciarService;



    @PostMapping("/addLivroNaBiblioteca")
    public ResponseEntity<?> addLivroNaBiblioteca(@RequestBody BiblioGerencyDTO dto) {
        gerenciarService.addLivroNaBiblioteca(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{bibliotecaId}/Livros/{livroId}")
    public ResponseEntity<?> deleteLivroBiblioteca(@PathVariable Long bibliotecaId, @PathVariable Long livroId) {
        gerenciarService.deleteLivroBiblioteca(bibliotecaId, livroId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/livroPorTitulo")
    public ResponseEntity<List<LivroEncontradoBibliotecaDTO>> livroPorTitulo(@RequestParam("titulo") String titulo) {
        return ResponseEntity.ok(gerenciarService.livroPorTitulo(titulo));
    }


    @GetMapping("/livrosDaBiblioteca/{id}")
    public ResponseEntity<Page<LivroEncontradoBibliotecaDTO>> livrosDaBiblioteca(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity
                .ok(gerenciarService.livrosDaBiblioteca(id, pageable));
    }

    @GetMapping
    public ResponseEntity<Page<BiblioGerencyDTO2>> buscarVinculados(Pageable pageable) {
        return ResponseEntity
                .ok(gerenciarService.buscarVinculados(pageable));
    }

}