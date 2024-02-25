package com.itau.insurance.infrastructure.validation;

import java.math.BigDecimal;

import com.itau.insurance.application.gateway.ProdutoValidationGateway;
import com.itau.insurance.infrastructure.exceptions.ProdutoInvalidoException;

public class ProdutoValidation implements ProdutoValidationGateway {

	@Override
	public void validarNome(String nome) throws ProdutoInvalidoException {
		if (nome == null || nome.isBlank() || nome.isEmpty() || nome.length() < 2)
			throw new ProdutoInvalidoException(
				"Produto inválido. O nome não pode ser branco, nulo ou menor que 2 caracteres.");
	}

	@Override
	public void validarPrecoBase(BigDecimal precoBase) throws ProdutoInvalidoException {
		boolean precoBaseValido = precoBase.compareTo(BigDecimal.ZERO) > 0;
		if (!precoBaseValido)
			throw new ProdutoInvalidoException("Produto inválido. O preço base não pode ser menor ou igual a zero.");
	}
}
