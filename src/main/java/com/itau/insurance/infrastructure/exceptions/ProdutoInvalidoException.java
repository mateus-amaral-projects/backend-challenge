package com.itau.insurance.infrastructure.exceptions;

public class ProdutoInvalidoException extends RuntimeException {
	private static final long serialVersionUID = -200574535552509717L;
	
	public ProdutoInvalidoException(String mensagem) {
		super(mensagem);
	}
}
