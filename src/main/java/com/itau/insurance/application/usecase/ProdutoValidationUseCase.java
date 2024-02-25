package com.itau.insurance.application.usecase;

import java.math.BigDecimal;

import com.itau.insurance.application.gateway.ProdutoValidationGateway;
import com.itau.insurance.infrastructure.exceptions.ProdutoInvalidoException;

public class ProdutoValidationUseCase {
	private ProdutoValidationGateway produtoValidationGateway;
	
	public ProdutoValidationUseCase(ProdutoValidationGateway produtoValidationGateway) {
		this.produtoValidationGateway = produtoValidationGateway;
	}
	
	public void validarNome(String nome) throws ProdutoInvalidoException {
		produtoValidationGateway.validarNome(nome);
	}

	public void validarPrecoBase(BigDecimal precoBase) throws ProdutoInvalidoException {
		produtoValidationGateway.validarPrecoBase(precoBase);
	}
}
