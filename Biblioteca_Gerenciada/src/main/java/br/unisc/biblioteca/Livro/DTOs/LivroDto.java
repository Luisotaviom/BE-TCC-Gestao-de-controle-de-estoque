package br.unisc.biblioteca.Livro.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivroDto {
    Long id;
    String titulo;
    String categoria;
    Integer anopubli;
    String autor;
    Long isbn;
}
