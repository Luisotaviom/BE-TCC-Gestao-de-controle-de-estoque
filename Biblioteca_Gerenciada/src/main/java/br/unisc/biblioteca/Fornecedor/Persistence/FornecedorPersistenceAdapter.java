package br.unisc.biblioteca.Fornecedor.Persistence;

import br.unisc.biblioteca.Fornecedor.Repository.FornecedorRepository;
import br.unisc.biblioteca.Fornecedor.DTOs.FornecedorDTO;
import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import br.unisc.biblioteca.Movimentacoes.Banco.MovimentacaoEntity;
import jakarta.persistence.EntityNotFoundException;
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
    public void createFornecedor(FornecedorDTO fornecedorDTO) {
        var fornecedorEntidade = FornecedorEntity.criarEntidade(fornecedorDTO);
        repository.save(fornecedorEntidade);
    }

    @Override
    public void deleteFornecedor(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateFornecedor(Long id, FornecedorDTO fornecedorDTO) {
        var entidadeOptional = repository.findById(id);
        if (entidadeOptional.isPresent()) {
            var entidade = entidadeOptional.get();
            entidade.setNome(StringUtils.isBlank(fornecedorDTO.getNome()) ? entidade.getNome() : fornecedorDTO.getNome());
            entidade.setCidade(StringUtils.isBlank(fornecedorDTO.getCidade()) ? entidade.getCidade() : fornecedorDTO.getCidade());
            entidade.setCelular(StringUtils.isBlank(fornecedorDTO.getCelular()) ? entidade.getCelular() : fornecedorDTO.getCelular());
            entidade.setEmail(StringUtils.isBlank(fornecedorDTO.getEmail()) ? entidade.getEmail() : fornecedorDTO.getEmail());

            // Adicionar lógica para atualizar o status de ativação
            if (fornecedorDTO.getAtivo() != null) { // Supondo que getAtivo retorna Boolean para lidar com null
                entidade.setAtivo(fornecedorDTO.getAtivo());
            }
            repository.save(entidade);
        } else {
            // Lançar uma exceção específica que pode ser definida em seu projeto
            throw new EntityNotFoundException("Fornecedor não encontrado com ID: " + id);
        }
    }

    @Override
    public Page<FornecedorDTO> buscarFornecedor(Pageable pageable) {
        return repository.findAll(pageable)
                .map(FornecedorEntity::converterEntidadeParaDto);
    }


    @Override
    public Object buscarPorIdFornecedor(Long id) {
        Optional<FornecedorEntity> fornecedorOptional = repository.findById(id);

        if (fornecedorOptional.isPresent()) {
            FornecedorEntity fornecedor = fornecedorOptional.get();
            return FornecedorEntity.converterEntidadeParaDto(fornecedor);
        } else {
            return null;
        }
    }

    @Override
    public Page<FornecedorEntity> buscarFornecedoresPorStatus(boolean ativo, Pageable pageable) {
        return repository.findByAtivo(ativo, pageable);
    }

    @Override
    public Page<FornecedorEntity> buscarTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }
}