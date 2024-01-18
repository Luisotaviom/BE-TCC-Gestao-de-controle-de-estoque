package br.unisc.biblioteca.Movimentacoes.Repository;

import br.unisc.biblioteca.Movimentacoes.Banco.MovimentacaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEntity, Long> {
    Page<MovimentacaoEntity> findByTipo(String tipo, Pageable pageable);
}
