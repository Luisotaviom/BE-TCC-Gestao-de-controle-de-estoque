package br.unisc.biblioteca.Gerency.Repository;

import br.unisc.biblioteca.Gerency.Banco.GerenciaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GerencyRepository extends JpaRepository<GerenciaEntity, Long> {
    @Query("SELECT CASE WHEN COUNT(gerency) > 0 THEN true ELSE false END FROM GerenciaEntity gerency WHERE " +
            "gerency.produto.id = :ProdutoId AND " +
            "gerency.fornecedor.id = :FornecedorId")
    void existsByBibliotecaIdAndLivroId(@Param("FornecedorId") Long FornecedorId, @Param("ProdutoId") Long ProdutoId);

    void deleteByFornecedorIdAndProdutoId(Long FornecedorId, Long ProdutoId);

    List<GerenciaEntity> findAllByProdutoNomeContaining(String nome);

    Page<GerenciaEntity> findByFornecedorId(Long FornecedorId, Pageable pageable);

}