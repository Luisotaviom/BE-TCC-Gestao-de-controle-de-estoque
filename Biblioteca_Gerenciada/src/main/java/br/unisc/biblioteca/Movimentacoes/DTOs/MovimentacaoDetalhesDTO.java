package br.unisc.biblioteca.Movimentacoes.DTOs;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoDetalhesDTO extends MovimentacaoDTO {
    private String produtoNome;
    private String fornecedorNome;
    private String categoria;
}

