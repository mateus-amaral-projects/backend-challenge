package com.itau.insurance.infrastructure.exceptions;

public class ProdutoNaoEncontradoException extends RuntimeException {
	private static final long serialVersionUID = 5538670353034915571L;

	public ProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
}
