package br.unisc.biblioteca.Produto.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutosDoFornecedorDto {
    private Long id;
    private String nome;
    private String categoria;
}

