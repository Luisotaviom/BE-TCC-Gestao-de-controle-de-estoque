package br.unisc.biblioteca.Movimentacoes.Banco;

import br.unisc.biblioteca.Fornecedor.Banco.FornecedorEntity;
import br.unisc.biblioteca.Fornecedor.Repository.FornecedorRepository;
import br.unisc.biblioteca.Movimentacoes.DTOs.MovimentacaoDTO;
import br.unisc.biblioteca.Movimentacoes.DTOs.MovimentacaoDetalhesDTO;
import br.unisc.biblioteca.Movimentacoes.DTOs.RelatorioMovimentacaoDto;
import br.unisc.biblioteca.Produto.Banco.ProdutoEntity;
import br.unisc.biblioteca.Produto.DTOs.ProdutoDto;
import br.unisc.biblioteca.Produto.DTOs.ProdutosDoFornecedorDto;
import br.unisc.biblioteca.Produto.Repository.ProdutoRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "movimentacoes")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovimentacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(length = 1, nullable = false)
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "produto_id", referencedColumnName = "id")
    private ProdutoEntity produto;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", referencedColumnName = "id")
    private FornecedorEntity fornecedor;

    public static MovimentacaoDetalhesDTO convertEntidadeParaDto(MovimentacaoEntity entity) {
        MovimentacaoDetalhesDTO dto = new MovimentacaoDetalhesDTO();
        dto.setId(entity.getId());
        dto.setProduto_id(entity.getProduto().getId());
        dto.setProdutoNome(entity.getProduto().getNome()); // Assumindo que ProdutoEntity tenha um campo 'nome'
        dto.setQuantidade(entity.getQuantidade());
        dto.setValor(entity.getValor());
        dto.setTipo(entity.getTipo());
        dto.setDataRegistro(entity.getDataRegistro());
        if (entity.getFornecedor() != null) {
            dto.setFornecedor_id(entity.getFornecedor().getId());
            dto.setFornecedorNome(entity.getFornecedor().getNome()); // Assumindo que FornecedorEntity tenha um campo 'nome'
        }
        return dto;
    }


    public static MovimentacaoEntity criarEntidade(MovimentacaoDTO dto, FornecedorEntity fornecedor, ProdutoEntity produto) {
        return MovimentacaoEntity.builder()
                .produto(produto)
                .quantidade(dto.getQuantidade())
                .valor(dto.getValor())
                .tipo(dto.getTipo())
                .dataRegistro(dto.getDataRegistro())
                .fornecedor(fornecedor)
                .build();
    }



    public static RelatorioMovimentacaoDto converterEntidadeParaDto(MovimentacaoEntity entidade) {
        RelatorioMovimentacaoDto dto = new RelatorioMovimentacaoDto();
        dto.setId(entidade.getId());
        dto.setProduto_id(entidade.getProduto().getId()); // Supondo que existe um método getProduto() na entidade.
        dto.setQuantidade(entidade.getQuantidade());
        dto.setValor(entidade.getValor());
        dto.setTipo(entidade.getTipo());
        dto.setDataRegistro(entidade.getDataRegistro());
        dto.setFornecedor_id(entidade.getFornecedor().getId()); // Supondo que existe um método getFornecedor() na entidade.
        return dto;
    }



}



