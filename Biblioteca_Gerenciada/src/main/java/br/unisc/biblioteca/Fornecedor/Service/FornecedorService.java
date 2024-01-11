package br.unisc.biblioteca.Biblioteca.Service;

import br.unisc.biblioteca.Biblioteca.DTOs.FornecedorDTO;
import br.unisc.biblioteca.Biblioteca.Persistence.FornecedorPersistence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Slf4j
@Service
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FornecedorService {

    private final FornecedorPersistence persistence;

    public void createBiblioteca(FornecedorDTO fornecedorDTO) {
        persistence.createBiblioteca(fornecedorDTO);
    }

    public void deleteBiblioteca(Long id) {
        persistence.deleteBiblioteca(id);
    }

    public void updateBiblioteca(Long id, FornecedorDTO fornecedorDTO) {
        persistence.updateBiblioteca(id, fornecedorDTO);
    }

    public Page<FornecedorDTO> buscarBiblioteca(Pageable pageable) {
        return persistence.buscarBiblioteca(pageable);
    }

    public Object buscarPorIdBiblio(Long id) {return persistence.buscarPorIdBiblio(id);}

}