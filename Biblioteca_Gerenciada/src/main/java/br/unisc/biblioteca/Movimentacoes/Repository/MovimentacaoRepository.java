package br.unisc.biblioteca.Movimentacoes.Repository;

import br.unisc.biblioteca.Movimentacoes.Banco.MovimentacaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEntity, Long> {
    Page<MovimentacaoEntity> findByTipo(String tipo, Pageable pageable);

    Page<MovimentacaoEntity> findAllByDataRegistroBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<MovimentacaoEntity> findByTipoAndDataRegistroBetween(String tipo, LocalDateTime start, LocalDateTime end, Pageable pageable);


}
