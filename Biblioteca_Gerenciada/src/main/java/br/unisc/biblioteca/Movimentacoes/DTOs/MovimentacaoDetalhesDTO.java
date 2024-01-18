package br.unisc.biblioteca.Movimentacoes.DTOs;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Value
@EqualsAndHashCode(callSuper = true)
public class MovimentacaoDetalhesDTO extends MovimentacaoDTO {

    private String produtoNome;
    private String fornecedorNome;

    public MovimentacaoDetalhesDTO(Long id, Long produtoId, Integer quantidade, BigDecimal valor, String tipo,
                                   LocalDateTime dataRegistro, Long fornecedorId, String produtoNome, String fornecedorNome) {
        super(id, produtoId, quantidade, valor, tipo, dataRegistro, fornecedorId);
        this.produtoNome = produtoNome;
        this.fornecedorNome = fornecedorNome;
    }
}

