package br.unisc.biblioteca.Biblioteca.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FornecedorDTO {
    Long id;
    String nome;
    String cidade;
    String email;
    Long celular;
}