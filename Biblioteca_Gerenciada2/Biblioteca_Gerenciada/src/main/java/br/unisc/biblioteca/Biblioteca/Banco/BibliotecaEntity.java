package br.unisc.biblioteca.Biblioteca.Banco;

import br.unisc.biblioteca.Biblioteca.DTOs.BibliotecaDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Biblioteca")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BibliotecaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String nome;

    public static BibliotecaDTO converterEntidadeParaDto(BibliotecaEntity entidade) {
        return new BibliotecaDTO(entidade.getId(), entidade.getNome());
    }

    public static BibliotecaEntity criarEntidade(BibliotecaDTO dto) {
        return BibliotecaEntity.builder()
                .nome(dto.getNome())
                .build();
    }
}