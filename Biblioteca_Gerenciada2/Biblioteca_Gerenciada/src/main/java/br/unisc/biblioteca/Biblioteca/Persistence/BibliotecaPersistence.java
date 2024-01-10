package br.unisc.biblioteca.Biblioteca.Persistence;

import br.unisc.biblioteca.Biblioteca.DTOs.BibliotecaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BibliotecaPersistence {

    void createBiblioteca(BibliotecaDTO bibliotecaDTO);

    void deleteBiblioteca(Long id);

    void updateBiblioteca(Long id, BibliotecaDTO bibliotecaDTO);

    Page<BibliotecaDTO> buscarBiblioteca(Pageable pageable);

    Object buscarPorIdBiblio(Long id);
}