package br.unisc.biblioteca.BiblioGerency.Persistence;

import br.unisc.biblioteca.BiblioGerency.Banco.BiblioGerencyEntity;
import br.unisc.biblioteca.BiblioGerency.DTOs.BiblioGerencyDTO2;
import br.unisc.biblioteca.BiblioGerency.DTOs.LivroEncontradoBibliotecaDTO;
import br.unisc.biblioteca.BiblioGerency.Repository.BiblioGerencyRepository;
import br.unisc.biblioteca.Biblioteca.Banco.BibliotecaEntity;
import br.unisc.biblioteca.BiblioGerency.DTOs.BiblioGerencyDTO;
import br.unisc.biblioteca.Biblioteca.DTOs.BibliotecaDTO;
import br.unisc.biblioteca.Biblioteca.Repository.BibliotecaRepository;
import br.unisc.biblioteca.Livro.Banco.LivroEntity;
import br.unisc.biblioteca.Livro.Repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BiblioGerencyPersistenceAdapter implements BiblioGerencyPersistence {

    private final BibliotecaRepository bibliotecarepository;
    private final LivroRepository livroRepository;
    private final BiblioGerencyRepository biblioGerencyRepository;

    @Override
    public void addLivroNaBiblioteca(BiblioGerencyDTO dto) {
        BibliotecaEntity biblioteca = bibliotecarepository.findById(dto.getBibliotecaId())
                .orElseThrow();

        LivroEntity livro = livroRepository.findById(dto.getLivroId())
                .orElseThrow();

        BiblioGerencyEntity bibliotecaLivro = new BiblioGerencyEntity();
        bibliotecaLivro.setBiblioteca(biblioteca);
        bibliotecaLivro.setLivro(livro);
        biblioGerencyRepository.save(bibliotecaLivro);
    }

        @Override
        @Transactional
        public void deleteLivroBiblioteca(Long bibliotecaId, Long livroId) {
            biblioGerencyRepository.existsByBibliotecaIdAndLivroId(bibliotecaId, livroId);
            biblioGerencyRepository.deleteByBibliotecaIdAndLivroId(bibliotecaId, livroId);
        }

    @Override
    public List<LivroEncontradoBibliotecaDTO> livroPorTitulo(String titulo) {
        return biblioGerencyRepository.findAllByLivroTituloContaining(titulo)
                .stream()
                .map(BiblioGerencyEntity::convertEntityToDTO)
                .toList();
    }

    @Override
    public Page<LivroEncontradoBibliotecaDTO> livrosDaBiblioteca(Long bibliotecaId, Pageable pageable) {
        var entidadeOptional = biblioGerencyRepository.findByBibliotecaId(bibliotecaId, pageable);
        entidadeOptional.isEmpty();
        Page<BiblioGerencyEntity> bibliotecaLivros = biblioGerencyRepository.findByBibliotecaId(bibliotecaId, pageable);
        return bibliotecaLivros.map(BiblioGerencyEntity::convertEntityToDTO);
    }

    @Override
    public Page<BiblioGerencyDTO2> buscarVinculados(Pageable pageable) {
        return biblioGerencyRepository.findAll(pageable)
                .map(BiblioGerencyEntity::convertEntidadeParaDTO);
    }
}