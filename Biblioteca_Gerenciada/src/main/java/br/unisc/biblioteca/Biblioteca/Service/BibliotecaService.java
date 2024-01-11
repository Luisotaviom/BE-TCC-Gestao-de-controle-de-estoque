package br.unisc.biblioteca.Biblioteca.Service;

import br.unisc.biblioteca.Biblioteca.DTOs.BibliotecaDTO;
import br.unisc.biblioteca.Biblioteca.Persistence.BibliotecaPersistence;
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
public class BibliotecaService {

    private final BibliotecaPersistence persistence;

    public void createBiblioteca(BibliotecaDTO bibliotecaDTO) {
        persistence.createBiblioteca(bibliotecaDTO);
    }

    public void deleteBiblioteca(Long id) {
        persistence.deleteBiblioteca(id);
    }

    public void updateBiblioteca(Long id, BibliotecaDTO bibliotecaDTO) {
        persistence.updateBiblioteca(id, bibliotecaDTO);
    }

    public Page<BibliotecaDTO> buscarBiblioteca(Pageable pageable) {
        return persistence.buscarBiblioteca(pageable);
    }

    public Object buscarPorIdBiblio(Long id) {return persistence.buscarPorIdBiblio(id);}

}