package br.unisc.biblioteca.BiblioGerency.Service;

import br.unisc.biblioteca.BiblioGerency.DTOs.BiblioGerencyDTO2;
import br.unisc.biblioteca.BiblioGerency.Persistence.BiblioGerencyPersistence;
import br.unisc.biblioteca.BiblioGerency.DTOs.BiblioGerencyDTO;
import br.unisc.biblioteca.BiblioGerency.DTOs.LivroEncontradoBibliotecaDTO;
import br.unisc.biblioteca.Biblioteca.DTOs.BibliotecaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GerencyBiblioService {

    private final BiblioGerencyPersistence persistence;

    public void addLivroNaBiblioteca(BiblioGerencyDTO dto) {
        persistence.addLivroNaBiblioteca(dto);
    }

    public void deleteLivroBiblioteca(Long bibliotecaId, Long livroId) {
        persistence.deleteLivroBiblioteca(bibliotecaId, livroId);
    }

    public List<LivroEncontradoBibliotecaDTO> livroPorTitulo(String titulo) {
        return persistence.livroPorTitulo(titulo);
    }

    public Page<LivroEncontradoBibliotecaDTO> livrosDaBiblioteca(Long bibliotecaId, Pageable pageable) {
        return persistence.livrosDaBiblioteca(bibliotecaId, pageable);
    }

    public Page<BiblioGerencyDTO2> buscarVinculados(Pageable pageable) {
        return persistence.buscarVinculados(pageable);
    }
}