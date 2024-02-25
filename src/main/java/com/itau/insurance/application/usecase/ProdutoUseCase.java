package com.itau.insurance.application.usecase;

import com.itau.insurance.application.gateway.ProdutoGateway;
import com.itau.insurance.domain.Produto;

public class ProdutoUseCase {
	private ProdutoGateway produtoGateway;
	
	public ProdutoUseCase(ProdutoGateway produtoGateway) {
		this.produtoGateway = produtoGateway;
	}
	
	public boolean existeProdutoPorId(Produto produto) {
		return produtoGateway.existeProdutoPorId(produto);
	}
	
	public boolean existeProdutoPorNomeECategoria(Produto produto) {
		return produtoGateway.existeProdutoPorNomeECategoria(produto);
	}
	
	public Produto criarProduto(Produto produto) {
		return produtoGateway.criarProduto(produto);
	}

	public Produto alterarProduto(Produto produto) {
		return produtoGateway.alterarProduto(produto);
	}
}
