package br.unisc.biblioteca.Biblioteca.Persistence;

import br.unisc.biblioteca.Biblioteca.Repository.BibliotecaRepository;
import br.unisc.biblioteca.Biblioteca.DTOs.BibliotecaDTO;
import br.unisc.biblioteca.Biblioteca.Banco.BibliotecaEntity;
import br.unisc.biblioteca.Livro.Banco.LivroEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BibliotecaPersistenceAdapter implements BibliotecaPersistence {

    private final BibliotecaRepository repository;

    @Override
    public void createBiblioteca(BibliotecaDTO bibliotecaDTO) {
        var bibliotecaEntidade = BibliotecaEntity.criarEntidade(bibliotecaDTO);
        repository.save(bibliotecaEntidade);
    }

    @Override
    public void deleteBiblioteca(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateBiblioteca(Long id, BibliotecaDTO bibliotecaDTO) {
        var entidadeOptional = repository.findById(id);
        var entidade = entidadeOptional.get();
        entidade.setNome(StringUtils.isBlank(bibliotecaDTO.getNome()) ? entidade.getNome() : bibliotecaDTO.getNome());
        repository.save(entidade);
    }

    @Override
    public Page<BibliotecaDTO> buscarBiblioteca(Pageable pageable) {
        return repository.findAll(pageable)
                .map(BibliotecaEntity::converterEntidadeParaDto);
    }


    @Override
    public Object buscarPorIdBiblio(Long id) {
        Optional<BibliotecaEntity> biblioOptional = repository.findById(id);

        if (biblioOptional.isPresent()) {
            BibliotecaEntity biblioteca = biblioOptional.get();
            return BibliotecaEntity.converterEntidadeParaDto(biblioteca);
        } else {
            return null;
        }
    }
}