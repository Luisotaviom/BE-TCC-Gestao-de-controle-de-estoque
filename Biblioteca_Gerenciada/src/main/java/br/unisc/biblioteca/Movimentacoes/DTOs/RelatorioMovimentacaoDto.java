package br.unisc.biblioteca.Movimentacoes.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioMovimentacaoDto {
    private Long id;

    private Long produto_id;

    private Integer quantidade;

    private BigDecimal valor;

    private String tipo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataRegistro;

    private Long fornecedor_id;

    private SomaInfo somaInfo;


}
