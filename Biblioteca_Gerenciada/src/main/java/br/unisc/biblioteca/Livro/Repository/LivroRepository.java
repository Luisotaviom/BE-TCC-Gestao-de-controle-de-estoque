package br.unisc.biblioteca.Livro.Repository;

import br.unisc.biblioteca.Livro.Banco.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<LivroEntity, Long> {
    Optional<LivroEntity> findByIsbn(Long codigoisbn);
}