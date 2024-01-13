package br.unisc.biblioteca.Gerency.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GerencyDTO {
    Long id;
    Long fornecedorId;
    Long produtoId;
}