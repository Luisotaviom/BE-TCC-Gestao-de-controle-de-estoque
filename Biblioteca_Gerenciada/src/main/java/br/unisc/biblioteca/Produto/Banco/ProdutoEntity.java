package br.unisc.biblioteca.Produto.Banco;

import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import br.unisc.biblioteca.Fornecedor.Repository.FornecedorRepository;
import br.unisc.biblioteca.Movimentacoes.Persistence.Exceptions.EntidadeNaoEncontradaException;
import br.unisc.biblioteca.Produto.DTOs.ProdutoDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Produtos")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String nome;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private FornecedorEntity fornecedor;

    @Column(nullable = false)
    String categoria;

    @Column(nullable = false, unique = true)
    Boolean ativo = true;


    public static ProdutoDto converterEntidadeParaDto(ProdutoEntity entidade) {
        ProdutoDto dto = new ProdutoDto();
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setCategoria(entidade.getCategoria());
        dto.setAtivo(entidade.getAtivo());
        if (entidade.getFornecedor() != null) {
            dto.setFornecedor_id(entidade.getFornecedor().getId());
            dto.setFornecedorNome(entidade.getFornecedor().getNome());
        }
        return dto;
    }
    public static ProdutoEntity criarEntidade(ProdutoDto dto, FornecedorEntity fornecedor) {
        return ProdutoEntity.builder()
                .nome(dto.getNome())
                .fornecedor(fornecedor)
                .categoria(dto.getCategoria())
                .ativo(dto.getAtivo())
                .build();
    }


}



