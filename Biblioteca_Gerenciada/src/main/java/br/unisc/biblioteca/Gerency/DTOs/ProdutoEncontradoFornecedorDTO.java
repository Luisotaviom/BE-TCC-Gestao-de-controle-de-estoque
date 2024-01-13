package br.unisc.biblioteca.Gerency.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

@Data
@NoArgsConstructor
@CrossOrigin(origins = "*")
public class ProdutoEncontradoFornecedorDTO {
    String nomeFornecedor;
    String nomeProduto;
}
