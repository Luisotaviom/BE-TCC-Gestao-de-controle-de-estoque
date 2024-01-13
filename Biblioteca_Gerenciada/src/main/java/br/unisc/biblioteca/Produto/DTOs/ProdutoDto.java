package br.unisc.biblioteca.Produto.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDto {
    Long id;
    String nome;
    String marca;
    String peso;
    Long preco_custo;
    Long preco_venda;
}
