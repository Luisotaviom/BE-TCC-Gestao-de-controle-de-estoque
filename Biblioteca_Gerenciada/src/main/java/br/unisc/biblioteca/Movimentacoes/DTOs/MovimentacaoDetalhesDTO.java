package br.unisc.biblioteca.Movimentacoes.DTOs;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoDetalhesDTO extends MovimentacaoDTO {
    private String produtoNome;
    private String fornecedorNome;
}

