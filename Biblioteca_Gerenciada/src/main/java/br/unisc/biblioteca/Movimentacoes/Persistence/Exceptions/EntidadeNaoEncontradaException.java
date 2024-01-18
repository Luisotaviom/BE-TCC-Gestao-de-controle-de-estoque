package br.unisc.biblioteca.Movimentacoes.Persistence.Exceptions;

public class EntidadeNaoEncontradaException extends RuntimeException {

    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }
}