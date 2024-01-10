package br.unisc.biblioteca.BiblioGerency.Persistence;

import br.unisc.biblioteca.BiblioGerency.DTOs.BiblioGerencyDTO2;
import br.unisc.biblioteca.BiblioGerency.DTOs.LivroEncontradoBibliotecaDTO;
import br.unisc.biblioteca.BiblioGerency.DTOs.BiblioGerencyDTO;
import br.unisc.biblioteca.Biblioteca.DTOs.BibliotecaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BiblioGerencyPersistence {

    void addLivroNaBiblioteca(BiblioGerencyDTO dto);

    void deleteLivroBiblioteca(Long bibliotecaId, Long livroId);

    List<LivroEncontradoBibliotecaDTO> livroPorTitulo(String titulo);

    Page<LivroEncontradoBibliotecaDTO> livrosDaBiblioteca(Long bibliotecaId, Pageable pageable);

    Page<BiblioGerencyDTO2> buscarVinculados(Pageable pageable);
}
