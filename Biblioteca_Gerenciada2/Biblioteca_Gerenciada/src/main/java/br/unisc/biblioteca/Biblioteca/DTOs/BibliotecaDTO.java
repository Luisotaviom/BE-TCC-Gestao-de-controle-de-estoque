package br.unisc.biblioteca.Biblioteca.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BibliotecaDTO {
    Long id;
    String nome;
}