package br.unisc.biblioteca.Livro.Persistence;

import br.unisc.biblioteca.Livro.DTOs.LivroDto;
import br.unisc.biblioteca.Livro.Banco.LivroEntity;
import br.unisc.biblioteca.Livro.Repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class LivroPersistenceAdapter implements LivroPersistence {

    private final LivroRepository repository;

    @Override
    public void criarLivro(LivroDto livroDto) {
        repository.findByIsbn(livroDto.getIsbn());
        var livroEntidade = LivroEntity.criarEntidade(livroDto);
        repository.save(livroEntidade);
    }

    @Override
    public void updateLivro(Long id, LivroDto livroDto) {
        var entidadeOptional = repository.findById(id);
        var entidade = entidadeOptional.get();
        entidade.setTitulo(StringUtils.isBlank(livroDto.getTitulo()) ? entidade.getTitulo() : livroDto.getTitulo());
        entidade.setCategoria(StringUtils.isBlank(livroDto.getCategoria()) ? entidade.getCategoria() : livroDto.getCategoria());
        entidade.setAutor(StringUtils.isBlank(livroDto.getAutor()) ? entidade.getAutor() : livroDto.getAutor());
        entidade.setAnopubli(livroDto.getAnopubli());
        entidade.setIsbn(livroDto.getIsbn());

        repository.save(entidade);
    }

    @Override
    public void deleteLivro(Long id) {
        repository.findById(id);
        repository.deleteById(id);
    }

    @Override
    public Page<LivroDto> buscarTodosLivros(Pageable pageable) {
        return repository.findAll(pageable)
                .map(LivroEntity::converterEntidadeParaDto);
    }

    @Override
    public Object buscarPorId(Long id) {
        Optional<LivroEntity> livroOptional = repository.findById(id);

        if (livroOptional.isPresent()) {
            LivroEntity livro = livroOptional.get();
            return LivroEntity.converterEntidadeParaDto(livro);
        } else {
            return null;
        }
    }

}