package br.unisc.biblioteca.Fornecedor.Persistence;

import br.unisc.biblioteca.Fornecedor.Repository.FornecedorRepository;
import br.unisc.biblioteca.Fornecedor.DTOs.FornecedorDTO;
import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FornecedorPersistenceAdapter implements FornecedorPersistence {

    private final FornecedorRepository repository;

    @Override
    public void createBiblioteca(FornecedorDTO fornecedorDTO) {
        var bibliotecaEntidade = FornecedorEntity.criarEntidade(fornecedorDTO);
        repository.save(bibliotecaEntidade);
    }

    @Override
    public void deleteBiblioteca(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateBiblioteca(Long id, FornecedorDTO fornecedorDTO) {
        var entidadeOptional = repository.findById(id);
        var entidade = entidadeOptional.get();
        entidade.setNome(StringUtils.isBlank(fornecedorDTO.getNome()) ? entidade.getNome() : fornecedorDTO.getNome());
        entidade.setCidade(StringUtils.isBlank(fornecedorDTO.getCidade()) ? entidade.getCidade() : fornecedorDTO.getCidade());
        entidade.setEmail(StringUtils.isBlank(fornecedorDTO.getEmail()) ? entidade.getEmail() : fornecedorDTO.getEmail());
        entidade.setCelular(fornecedorDTO.getCelular());

        repository.save(entidade);
    }

    @Override
    public Page<FornecedorDTO> buscarBiblioteca(Pageable pageable) {
        return repository.findAll(pageable)
                .map(FornecedorEntity::converterEntidadeParaDto);
    }


    @Override
    public Object buscarPorIdBiblio(Long id) {
        Optional<FornecedorEntity> biblioOptional = repository.findById(id);

        if (biblioOptional.isPresent()) {
            FornecedorEntity biblioteca = biblioOptional.get();
            return FornecedorEntity.converterEntidadeParaDto(biblioteca);
        } else {
            return null;
        }
    }
}