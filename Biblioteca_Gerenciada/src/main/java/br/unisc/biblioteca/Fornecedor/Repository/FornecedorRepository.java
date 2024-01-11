package br.unisc.biblioteca.Biblioteca.Repository;

import br.unisc.biblioteca.Biblioteca.Banco.FornecedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Long> {
}