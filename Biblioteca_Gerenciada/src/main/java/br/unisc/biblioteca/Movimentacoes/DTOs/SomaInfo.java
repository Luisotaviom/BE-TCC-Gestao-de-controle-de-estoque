package br.unisc.biblioteca.Movimentacoes.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SomaInfo {
    private BigDecimal somaValores;
    private Integer somaQuantidades;
}