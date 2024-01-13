package br.unisc.biblioteca.Fornecedor.Repository;

import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Long> {
}