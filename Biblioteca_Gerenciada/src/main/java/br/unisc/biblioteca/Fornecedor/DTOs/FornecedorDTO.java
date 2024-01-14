package br.unisc.biblioteca.Fornecedor.DTOs;

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
    String celular;
    String email;
}