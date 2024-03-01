package br.unisc.biblioteca.Movimentacoes.Repository;

import br.unisc.biblioteca.Movimentacoes.Banco.MovimentacaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEntity, Long> {

    @Query(value = "SELECT m FROM MovimentacaoEntity m JOIN m.produto p " +
            "WHERE (:tipo IS NULL OR m.tipo = :tipo) " +
            "AND (:categoria IS NULL OR p.categoria = :categoria)",
            countQuery = "SELECT count(m) FROM MovimentacaoEntity m JOIN m.produto p " +
                    "WHERE (:tipo IS NULL OR m.tipo = :tipo) " +
                    "AND (:categoria IS NULL OR p.categoria = :categoria)",
            nativeQuery = false)
    Page<MovimentacaoEntity> findByTipoAndCategoria(
            @Param("tipo") String tipo,
            @Param("categoria") String categoria,
            Pageable pageable);

    Page<MovimentacaoEntity> findAll(Pageable pageable);

    @Query("SELECT m FROM MovimentacaoEntity m JOIN m.produto p JOIN m.fornecedor f WHERE (:nome IS NULL OR f.nome LIKE %:nome%) AND (:tipo IS NULL OR m.tipo = :tipo) AND (:categoria IS NULL OR p.categoria = :categoria) AND m.dataRegistro BETWEEN :inicio AND :fim")
    Page<MovimentacaoEntity> buscaNomeETipoECategoriaEData(@Param("nome") String nome, @Param("tipo") String tipo, @Param("categoria") String categoria, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim, Pageable pageable);




}
