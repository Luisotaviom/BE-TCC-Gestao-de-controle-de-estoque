package br.unisc.biblioteca.Produto_1.Repository;

import br.unisc.biblioteca.Produto_1.Banco.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
    Optional<ProdutoEntity> findById(Long id);
}