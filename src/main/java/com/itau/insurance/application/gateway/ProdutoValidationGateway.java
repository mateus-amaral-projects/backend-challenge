package com.itau.insurance.application.gateway;

import java.math.BigDecimal;

import com.itau.insurance.infrastructure.exceptions.ProdutoInvalidoException;

public interface ProdutoValidationGateway {
	
	void validarNome(String nome) throws ProdutoInvalidoException;
	void validarPrecoBase(BigDecimal precoBase) throws ProdutoInvalidoException;
}
