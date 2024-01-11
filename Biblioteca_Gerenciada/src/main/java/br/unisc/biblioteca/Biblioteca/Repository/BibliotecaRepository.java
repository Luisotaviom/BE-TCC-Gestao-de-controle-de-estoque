package br.unisc.biblioteca.Biblioteca.Repository;

import br.unisc.biblioteca.Biblioteca.Banco.BibliotecaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibliotecaRepository extends JpaRepository<BibliotecaEntity, Long> {
}