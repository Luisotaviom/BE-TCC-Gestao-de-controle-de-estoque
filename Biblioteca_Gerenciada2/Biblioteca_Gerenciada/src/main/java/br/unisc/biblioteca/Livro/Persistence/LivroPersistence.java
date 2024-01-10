package br.unisc.biblioteca.Livro.Persistence;

import br.unisc.biblioteca.Livro.DTOs.LivroDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LivroPersistence {

    void criarLivro(LivroDto livroDto);

    void updateLivro(Long id, LivroDto livroDto);

    void deleteLivro(Long id);

    Page<LivroDto> buscarTodosLivros(Pageable pageable);

    Object buscarPorId(Long id);
}