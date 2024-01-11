package br.unisc.biblioteca.BiblioGerency.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

@Data
@NoArgsConstructor
@CrossOrigin(origins = "*")
public class LivroEncontradoBibliotecaDTO {
    String nomeBiblioteca;
    String tituloLivro;
    Long codigoisbn;
}
