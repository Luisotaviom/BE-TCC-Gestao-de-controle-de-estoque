package br.unisc.biblioteca.Movimentacoes.Persistence.Exceptions;

public class ValidacaoNegocioException extends RuntimeException {
    public ValidacaoNegocioException(String mensagem) {
        super(mensagem);
    }
}

