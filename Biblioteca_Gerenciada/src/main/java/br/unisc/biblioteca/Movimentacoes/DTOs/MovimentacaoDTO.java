package br.unisc.biblioteca.Movimentacoes.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoDTO {

    private Long id; // Opcional para criação

    private Long produto_id;

    private Integer quantidade;

    private BigDecimal valor;

    private String tipo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataRegistro;

    private Long fornecedor_id;
}
