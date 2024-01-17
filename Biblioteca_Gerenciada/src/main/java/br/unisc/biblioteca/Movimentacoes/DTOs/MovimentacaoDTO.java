package br.unisc.biblioteca.Movimentacoes.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoDTO {

    private Long id; // Opcional para criação
    private Long produto_id;
    private Integer quantidade;
    private BigDecimal valor;
    private String tipo; // Considerar usar Enum aqui
    private LocalDateTime dataRegistro;
    private Long fornecedor_id; // Opcional para saídas
}
