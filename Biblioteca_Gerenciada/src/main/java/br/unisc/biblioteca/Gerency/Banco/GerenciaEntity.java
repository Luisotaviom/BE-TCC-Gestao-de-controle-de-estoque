package br.unisc.biblioteca.Gerency.Banco;

import br.unisc.biblioteca.Gerency.DTOs.GerencyDTO2;
import br.unisc.biblioteca.Gerency.DTOs.ProdutoEncontradoFornecedorDTO;
import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import br.unisc.biblioteca.Produto.Banco.ProdutoEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Gerencia")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GerenciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "Fornecedor_FK", nullable = false)
    private FornecedorEntity fornecedor;

    @ManyToOne
    @JoinColumn(name = "Produto_FK", nullable = false)
    private ProdutoEntity produto;



    public static ProdutoEncontradoFornecedorDTO convertEntityToDTO(GerenciaEntity entidade) {
        var dto = new ProdutoEncontradoFornecedorDTO();
        dto.setNomeFornecedor(entidade.getFornecedor().getNome());
        dto.setNomeProduto(entidade.getProduto().getNome());
        return dto;
    }

    public static GerencyDTO2 convertEntidadeParaDTO(GerenciaEntity entidade) {
        var dto = new GerencyDTO2();

        dto.setNomeFornecedor(entidade.getFornecedor().getNome());
        dto.setNomeProduto(entidade.getProduto().getNome());
        return dto;
    }



}
