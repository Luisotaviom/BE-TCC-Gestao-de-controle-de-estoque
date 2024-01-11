package br.unisc.biblioteca.BiblioGerency.Repository;

import br.unisc.biblioteca.BiblioGerency.Banco.BiblioGerencyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiblioGerencyRepository extends JpaRepository<BiblioGerencyEntity, Long> {
    @Query("SELECT CASE WHEN COUNT(biblio) > 0 THEN true ELSE false END FROM BiblioGerencyEntity biblio WHERE " +
            "biblio.livro.id = :livroId AND " +
            "biblio.biblioteca.id = :bibliotecaId")
    void existsByBibliotecaIdAndLivroId(@Param("bibliotecaId") Long bibliotecaId, @Param("livroId") Long livroId);

    void deleteByBibliotecaIdAndLivroId(Long bibliotecaId, Long livroId);

    List<BiblioGerencyEntity> findAllByLivroTituloContaining(String titulo);

    Page<BiblioGerencyEntity> findByBibliotecaId(Long bibliotecaId, Pageable pageable);

}