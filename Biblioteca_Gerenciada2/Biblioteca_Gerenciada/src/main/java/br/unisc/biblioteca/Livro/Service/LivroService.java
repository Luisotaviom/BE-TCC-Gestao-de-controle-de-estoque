package br.unisc.biblioteca.Livro.Service;

import br.unisc.biblioteca.Livro.DTOs.LivroDto;
import br.unisc.biblioteca.Livro.Persistence.LivroPersistence;
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
public class LivroService {

    private final LivroPersistence persistence;

    public void criarLivro(LivroDto livroDto) {
        persistence.criarLivro(livroDto);
    }

    public void updateLivro(Long id, LivroDto livroDto) {
        persistence.updateLivro(id, livroDto);
    }

    public void deleteLivro(Long id) {
        persistence.deleteLivro(id);
    }

    public Page<LivroDto> buscarTodosLivros(Pageable pageable) {
        return persistence.buscarTodosLivros(pageable);
    }

    public Object buscarPorId(Long id) {return persistence.buscarPorId(id);}

}