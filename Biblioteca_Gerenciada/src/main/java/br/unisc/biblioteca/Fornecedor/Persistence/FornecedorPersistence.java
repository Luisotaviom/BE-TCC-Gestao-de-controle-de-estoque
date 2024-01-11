package br.unisc.biblioteca.Biblioteca.Persistence;

import br.unisc.biblioteca.Biblioteca.DTOs.FornecedorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FornecedorPersistence {

    void createBiblioteca(FornecedorDTO fornecedorDTO);

    void deleteBiblioteca(Long id);

    void updateBiblioteca(Long id, FornecedorDTO fornecedorDTO);

    Page<FornecedorDTO> buscarBiblioteca(Pageable pageable);

    Object buscarPorIdBiblio(Long id);
}