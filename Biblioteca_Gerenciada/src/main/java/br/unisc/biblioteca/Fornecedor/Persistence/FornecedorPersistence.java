package br.unisc.biblioteca.Fornecedor.Persistence;

import br.unisc.biblioteca.Fornecedor.DTOs.FornecedorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FornecedorPersistence {

    void createBiblioteca(FornecedorDTO fornecedorDTO);

    void deleteBiblioteca(Long id);

    void updateBiblioteca(Long id, FornecedorDTO fornecedorDTO);

    Page<FornecedorDTO> buscarBiblioteca(Pageable pageable);

    Object buscarPorIdBiblio(Long id);
}