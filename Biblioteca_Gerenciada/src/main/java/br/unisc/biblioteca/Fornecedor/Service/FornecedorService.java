package br.unisc.biblioteca.Fornecedor.Service;

import br.unisc.biblioteca.Fornecedor.DTOs.FornecedorDTO;
import br.unisc.biblioteca.Fornecedor.Persistence.FornecedorPersistence;
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

    public void createFornecedor(FornecedorDTO fornecedorDTO) {
        persistence.createFornecedor(fornecedorDTO);
    }

    public void deleteFornecedor(Long id) {
        persistence.deleteFornecedor(id);
    }

    public void updateFornecedor(Long id, FornecedorDTO fornecedorDTO) {
        persistence.updateFornecedor(id, fornecedorDTO);
    }

    public Page<FornecedorDTO> buscarFornecedor(Pageable pageable) {
        return persistence.buscarFornecedor(pageable);
    }

    public Object buscarPorIdFornecedor(Long id) {return persistence.buscarPorIdFornecedor(id);}

}