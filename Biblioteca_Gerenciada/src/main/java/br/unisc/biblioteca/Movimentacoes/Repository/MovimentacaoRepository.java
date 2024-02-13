package br.unisc.biblioteca.Movimentacoes.Repository;

import br.unisc.biblioteca.Movimentacoes.Banco.MovimentacaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEntity, Long> {
    Page<MovimentacaoEntity> findByTipo(String tipo, Pageable pageable);

    Page<MovimentacaoEntity> findByProdutoCategoria(String categoria, Pageable pageable);

    Page<MovimentacaoEntity> findAllByDataRegistroBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<MovimentacaoEntity> findByTipoAndDataRegistroBetween(String tipo, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<MovimentacaoEntity> findAll(Pageable pageable);

    @Query("SELECT SUM(m.valor) FROM MovimentacaoEntity m WHERE m.tipo = :tipo AND m.dataRegistro BETWEEN :start AND :end")
    BigDecimal calcularSomaValorPorTipoEData(String tipo, LocalDateTime start, LocalDateTime end);

    @Query("SELECT SUM(m.quantidade) FROM MovimentacaoEntity m WHERE m.tipo = :tipo AND m.dataRegistro BETWEEN :start AND :end")
    Integer calcularSomaQuantidadePorTipoEData(String tipo, LocalDateTime start, LocalDateTime end);

    @Query("SELECT m FROM MovimentacaoEntity m JOIN m.produto p WHERE (:tipo IS NULL OR m.tipo = :tipo) AND (:categoria IS NULL OR p.categoria = :categoria) AND m.dataRegistro BETWEEN :inicio AND :fim")
    Page<MovimentacaoEntity> buscarMovimentacoesPorTipoECategoriaEData(@Param("tipo") String tipo, @Param("categoria") String categoria, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim, Pageable pageable);





}
