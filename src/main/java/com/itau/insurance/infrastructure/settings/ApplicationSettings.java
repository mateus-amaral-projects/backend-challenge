package com.itau.insurance.infrastructure.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.itau.insurance.application.gateway.ProdutoGateway;
import com.itau.insurance.application.gateway.ProdutoValidationGateway;
import com.itau.insurance.application.usecase.ProdutoUseCase;
import com.itau.insurance.application.usecase.ProdutoValidationUseCase;
import com.itau.insurance.infrastructure.controller.ProdutoMapper;
import com.itau.insurance.infrastructure.gateway.ProdutoEntityMapper;
import com.itau.insurance.infrastructure.gateway.ProdutoRepositoryGateway;
import com.itau.insurance.infrastructure.persistence.ProdutoRepository;
import com.itau.insurance.infrastructure.validation.ProdutoValidation;

@Configuration
public class ApplicationSettings {
	@Bean
	public ProdutoMapper newProdutoMapper() {
		return new ProdutoMapper();
	}

	@Bean
	public ProdutoEntityMapper newProdutoEntityMapper() {
		return new ProdutoEntityMapper();
	}

	@Bean
	public ProdutoGateway newProdutoGateway(ProdutoRepository produtoRepository,
			ProdutoEntityMapper produtoEntityMapper) {
		return new ProdutoRepositoryGateway(produtoRepository, produtoEntityMapper);
	}

	@Bean
	public ProdutoUseCase newProdutoUseCase(ProdutoGateway produtoGateway) {
		return new ProdutoUseCase(produtoGateway);
	}

	@Bean
	public ProdutoValidationGateway newProdutoValidationGateway() {
		return new ProdutoValidation();
	}

	@Bean
	public ProdutoValidationUseCase newProdutoValidationUseCase(ProdutoValidationGateway produtoValidationGateway) {
		return new ProdutoValidationUseCase(produtoValidationGateway);
	}
}
