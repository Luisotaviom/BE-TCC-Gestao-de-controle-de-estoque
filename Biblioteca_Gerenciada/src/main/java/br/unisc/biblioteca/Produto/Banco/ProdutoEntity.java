package br.unisc.biblioteca.Produto_1.Banco;

import br.unisc.biblioteca.Produto_1.DTOs.ProdutoDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Produto")
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

    @Column(nullable = false)
    String marca;

    @Column(nullable = false)
    String peso;

    @Column(nullable = false)
    Long preco_custo;

    @Column(nullable = false, unique = true)
    Long preco_venda;

    public static ProdutoDto converterEntidadeParaDto(ProdutoEntity entidade) {
        return new ProdutoDto(entidade.getId(), entidade.getNome(), entidade.getMarca(), entidade.getPeso(), entidade.getPreco_custo(), entidade.getPreco_venda());
    }

    public static ProdutoEntity criarEntidade(ProdutoDto dto) {
        return ProdutoEntity.builder()
                .nome(dto.getNome())
                .marca(dto.getMarca())
                .peso(dto.getPeso())
                .preco_custo(dto.getPreco_custo())
                .preco_venda(dto.getPreco_venda())
                .build();
    }
}