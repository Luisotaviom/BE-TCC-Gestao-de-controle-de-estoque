package br.unisc.biblioteca.BiblioGerency.Banco;

import br.unisc.biblioteca.BiblioGerency.DTOs.BiblioGerencyDTO;
import br.unisc.biblioteca.BiblioGerency.DTOs.BiblioGerencyDTO2;
import br.unisc.biblioteca.BiblioGerency.DTOs.LivroEncontradoBibliotecaDTO;
import br.unisc.biblioteca.Biblioteca.Banco.BibliotecaEntity;
import br.unisc.biblioteca.Livro.Banco.LivroEntity;
import br.unisc.biblioteca.Livro.DTOs.LivroDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Livro_Biblio")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BiblioGerencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "Biblioteca_FK", nullable = false)
    private BibliotecaEntity biblioteca;

    @ManyToOne
    @JoinColumn(name = "Livro_FK", nullable = false)
    private LivroEntity livro;



    public static LivroEncontradoBibliotecaDTO convertEntityToDTO(BiblioGerencyEntity entidade) {
        var dto = new LivroEncontradoBibliotecaDTO();
        dto.setNomeBiblioteca(entidade.getBiblioteca().getNome());
        dto.setTituloLivro(entidade.getLivro().getTitulo());
        dto.setCodigoisbn(entidade.getLivro().getIsbn());
        return dto;
    }

    public static BiblioGerencyDTO2 convertEntidadeParaDTO(BiblioGerencyEntity entidade) {
        var dto = new BiblioGerencyDTO2();

        dto.setNomeBiblioteca(entidade.getBiblioteca().getNome());
        dto.setTitulo(entidade.getLivro().getTitulo());
        return dto;
    }



}
