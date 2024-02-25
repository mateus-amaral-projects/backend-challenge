package com.itau.insurance.application.gateway;

import com.itau.insurance.domain.Produto;

public interface ProdutoGateway {
	
	boolean existeProdutoPorId(Produto produto);
	boolean existeProdutoPorNomeECategoria(Produto produto);
	Produto criarProduto(Produto produto);
	Produto alterarProduto(Produto produto);
}
