package br.unisc.biblioteca.Livro.Banco;

import br.unisc.biblioteca.Livro.DTOs.LivroDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Livro")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LivroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String titulo;

    @Column(nullable = false)
    String categoria;

    @Column(nullable = false)
    Integer anopubli;

    @Column(nullable = false)
    String autor;

    @Column(nullable = false, unique = true)
    Long isbn;

    public static LivroDto converterEntidadeParaDto(LivroEntity entidade) {
        return new LivroDto(entidade.getId(), entidade.getTitulo(), entidade.getCategoria(), entidade.getAnopubli(), entidade.getAutor(), entidade.getIsbn());
    }

    public static LivroEntity criarEntidade(LivroDto dto) {
        return LivroEntity.builder()
                .titulo(dto.getTitulo())
                .categoria(dto.getCategoria())
                .anopubli(dto.getAnopubli())
                .autor(dto.getAutor())
                .isbn(dto.getIsbn())
                .build();
    }
}