package br.unisc.biblioteca.Produto.Repository;

import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import br.unisc.biblioteca.Produto.Banco.ProdutoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
    Optional<ProdutoEntity> findById(Long id);

    Page<ProdutoEntity> findByFornecedorId(Long fornecedorId, Pageable pageable);

    @Query("SELECT p FROM ProdutoEntity p JOIN FETCH p.fornecedor")
    Page<ProdutoEntity> findAllWithFornecedor(Pageable pageable);

    @Query("SELECT p FROM ProdutoEntity p WHERE (:nome IS NULL OR p.nome LIKE %:nome%) AND (:ativo IS NULL OR p.ativo = :ativo)")
    Page<ProdutoEntity> findByNomeContainingIgnoreCaseAndAtivo(String nome, Boolean ativo, Pageable pageable);

}