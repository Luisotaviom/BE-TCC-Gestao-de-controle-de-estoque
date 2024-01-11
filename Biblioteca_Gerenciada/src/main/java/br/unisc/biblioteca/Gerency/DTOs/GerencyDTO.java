package br.unisc.biblioteca.BiblioGerency.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GerencyDTO {
    Long id;
    Long fornecedorId;
    Long produtoId;
}