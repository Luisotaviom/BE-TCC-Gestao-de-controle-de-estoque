package br.unisc.biblioteca.Biblioteca.Controller;

import br.unisc.biblioteca.Biblioteca.DTOs.FornecedorDTO;
import br.unisc.biblioteca.Biblioteca.Service.FornecedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Fornecedores")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FornecedorController {
    private final FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<?> createFornecedor(@RequestBody FornecedorDTO fornecedorDTO) {
        fornecedorService.createBiblioteca(fornecedorDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBiblioteca(@PathVariable Long id) {
        fornecedorService.deleteBiblioteca(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBiblioteca(@RequestBody FornecedorDTO fornecedorDTO, @PathVariable Long id) {
        fornecedorService.updateBiblioteca(id, fornecedorDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping
    public ResponseEntity<Page<FornecedorDTO>> buscarBiblioteca(Pageable pageable) {
        return ResponseEntity
                .ok(fornecedorService.buscarBiblioteca(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorDTO> buscarPorIdBiblio(@PathVariable Long id) {
        var biblioteca = fornecedorService.buscarPorIdBiblio(id);
        return ResponseEntity.ok((FornecedorDTO) biblioteca);
    }
}