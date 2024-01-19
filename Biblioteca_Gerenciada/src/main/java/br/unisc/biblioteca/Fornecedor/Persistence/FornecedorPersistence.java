package br.unisc.biblioteca.Fornecedor.Persistence;

import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import br.unisc.biblioteca.Fornecedor.DTOs.FornecedorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FornecedorPersistence {

    void createFornecedor(FornecedorDTO fornecedorDTO);

    void deleteFornecedor(Long id);

    void updateFornecedor(Long id, FornecedorDTO fornecedorDTO);

    Page<FornecedorDTO> buscarFornecedor(Pageable pageable);

    Object buscarPorIdFornecedor(Long id);

    Page<FornecedorEntity> buscarFornecedoresPorStatus(boolean ativo, Pageable pageable);

    Page<FornecedorEntity> buscarTodos(Pageable pageable);

}