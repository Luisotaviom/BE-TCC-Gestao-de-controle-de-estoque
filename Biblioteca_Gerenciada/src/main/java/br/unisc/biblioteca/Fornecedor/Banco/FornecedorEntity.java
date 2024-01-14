package br.unisc.biblioteca.Fornecedor.Banco;

import br.unisc.biblioteca.Fornecedor.DTOs.FornecedorDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Fornecedores")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FornecedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String nome;

    @Column(nullable = false, unique = true)
    String cidade;

    @Column(nullable = false, unique = true)
    String celular;

    @Column(nullable = false, unique = true)
    String email;

    public static FornecedorDTO converterEntidadeParaDto(FornecedorEntity entidade) {
        return new FornecedorDTO(entidade.getId(), entidade.getNome(), entidade.getCidade(), entidade.getEmail(), entidade.getCelular());
    }

    public static FornecedorEntity criarEntidade(FornecedorDTO dto) {
        return FornecedorEntity.builder()
                .nome(dto.getNome())
                .cidade(dto.getCidade())
                .email(dto.getEmail())
                .celular(dto.getCelular())
                .build();
    }
}