package com.itau.insurance.infrastructure.gateway;

import com.itau.insurance.application.gateway.ProdutoGateway;
import com.itau.insurance.domain.Produto;
import com.itau.insurance.infrastructure.persistence.ProdutoEntity;
import com.itau.insurance.infrastructure.persistence.ProdutoRepository;

public class ProdutoRepositoryGateway implements ProdutoGateway {
	private final ProdutoRepository produtoRepository;
	private final ProdutoEntityMapper produtoEntityMapper;

	public ProdutoRepositoryGateway(ProdutoRepository produtoRepository, ProdutoEntityMapper produtoEntityMapper) {
		this.produtoRepository = produtoRepository;
		this.produtoEntityMapper = produtoEntityMapper;
	}
	
	@Override
	public boolean existeProdutoPorId(Produto produto) {
		ProdutoEntity produtoEntity = produtoEntityMapper.transformeParaProdutoEntity(produto);
		return produtoRepository.existsById(produtoEntity.getId());
	}
	
	@Override
	public boolean existeProdutoPorNomeECategoria(Produto produto) {
		ProdutoEntity produtoEntity = produtoEntityMapper.transformeParaProdutoEntity(produto);
		return produtoRepository.existsByNomeAndCategoria(produtoEntity.getNome(),produtoEntity.getCategoria());
	}
	
	@Override
	public Produto criarProduto(Produto produto) {
		ProdutoEntity produtoEntity = produtoEntityMapper.transformeParaProdutoEntity(produto);
		ProdutoEntity produtoEntityCriado = produtoRepository.save(produtoEntity);
		return produtoEntityMapper.transformeParaProduto(produtoEntityCriado);
	}

	@Override
	public Produto alterarProduto(Produto produto) {
		ProdutoEntity produtoEntity = produtoEntityMapper.transformeParaProdutoEntity(produto);
		ProdutoEntity produtoEntityAlterado = produtoRepository.save(produtoEntity);
		return produtoEntityMapper.transformeParaProduto(produtoEntityAlterado);
	}
}
