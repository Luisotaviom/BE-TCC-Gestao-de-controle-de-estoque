package br.unisc.biblioteca.Produto.DTOs;

public class ProdutosDoFornecedorDto {
    private Long id;
    private String nome;
    private String categoria;

    public ProdutosDoFornecedorDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public ProdutosDoFornecedorDto(Long id, String nome, String categoria) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
    }
}

