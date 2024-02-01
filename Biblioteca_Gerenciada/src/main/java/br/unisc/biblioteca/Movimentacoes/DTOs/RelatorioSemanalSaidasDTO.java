package br.unisc.biblioteca.Movimentacoes.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioSemanalSaidasDTO {
    private Integer somaQuantidadeSaidas;
    private BigDecimal somaValorSaidas;
}
