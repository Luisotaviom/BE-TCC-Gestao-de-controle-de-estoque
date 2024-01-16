package br.unisc.biblioteca.Produto.Banco;

import br.unisc.biblioteca.Produto.DTOs.ProdutoDto;
import br.unisc.biblioteca.Produto.DTOs.ProdutosDoFornecedorDto;
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

    @Column(nullable = false)
    Long fornecedor_id;

    @Column(nullable = false)
    String categoria;

    public static ProdutoDto converterEntidadeParaDto(ProdutoEntity entidade) {
        return new ProdutoDto(entidade.getId(), entidade.getNome(), entidade.getFornecedor_id(), entidade.getCategoria());
    }

    public static ProdutoEntity criarEntidade(ProdutoDto dto) {
        return ProdutoEntity.builder()
                .nome(dto.getNome())
                .fornecedor_id(dto.getFornecedor_id())
                .categoria(dto.getCategoria())
                .build();
    }

    public static ProdutosDoFornecedorDto convertProdutoToDTO(ProdutoEntity entidade) {
        var dto = new ProdutosDoFornecedorDto();
        dto.setNome(entidade.getNome()); // Mapear o nome do fornecedor
        dto.setCategoria(entidade.getCategoria()); // Mapear o nome do produto
        return dto;
    }


}



