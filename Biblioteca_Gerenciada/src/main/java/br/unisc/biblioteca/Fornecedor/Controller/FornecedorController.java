package br.unisc.biblioteca.Fornecedor.Controller;

import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import br.unisc.biblioteca.Fornecedor.DTOs.FornecedorDTO;
import br.unisc.biblioteca.Fornecedor.Service.FornecedorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        fornecedorService.createFornecedor(fornecedorDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFornecedor(@PathVariable Long id) {
        fornecedorService.deleteFornecedor(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFornecedor(@RequestBody FornecedorDTO fornecedorDTO, @PathVariable Long id) {
        try {
            fornecedorService.updateFornecedor(id, fornecedorDTO);
            return ResponseEntity.ok().build(); // ResponseEntity.ok() já retorna o status 200
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<FornecedorDTO>> buscarFornecedor(Pageable pageable) {
        return ResponseEntity
                .ok(fornecedorService.buscarFornecedor(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorDTO> buscarPorIdFornecedor(@PathVariable Long id) {
        var fornecedor = fornecedorService.buscarPorIdFornecedor(id);
        return ResponseEntity.ok((FornecedorDTO) fornecedor);
    }

    @GetMapping("/buscarPorStatus")
    public ResponseEntity<Page<FornecedorDTO>> listFornecedoresPorStatus(
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<FornecedorEntity> fornecedoresEntities;

        if (ativo != null) {
            fornecedoresEntities = fornecedorService.buscarFornecedoresPorStatus(ativo, pageable);
        } else {
            fornecedoresEntities = fornecedorService.buscarTodos(pageable);
        }

        Page<FornecedorDTO> fornecedoresDTOs = fornecedoresEntities.map(FornecedorEntity::converterEntidadeParaDto);

        return ResponseEntity.ok(fornecedoresDTOs);
    }


}