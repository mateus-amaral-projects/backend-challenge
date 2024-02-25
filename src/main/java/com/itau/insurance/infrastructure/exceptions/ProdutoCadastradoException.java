package com.itau.insurance.infrastructure.exceptions;

public class ProdutoCadastradoException extends RuntimeException {
	private static final long serialVersionUID = -200574535552509717L;
	
	public ProdutoCadastradoException(String mensagem) {
		super(mensagem);
	}
}
