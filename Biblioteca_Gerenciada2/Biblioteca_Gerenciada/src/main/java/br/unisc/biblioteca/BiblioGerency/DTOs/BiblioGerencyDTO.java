package br.unisc.biblioteca.BiblioGerency.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BiblioGerencyDTO {
    Long id;
    Long bibliotecaId;
    Long livroId;
}