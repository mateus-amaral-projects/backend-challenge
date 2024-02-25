package com.itau.insurance.infrastructure.gateway;

import com.itau.insurance.domain.Categoria;
import com.itau.insurance.domain.Produto;
import com.itau.insurance.infrastructure.persistence.ProdutoEntity;

public class ProdutoEntityMapper {
	
	public ProdutoEntity transformeParaProdutoEntity(Produto produto) {
		return new ProdutoEntity(
			produto.getId()
		  , produto.getNome()
		  , produto.getCategoria().name()
		  , produto.getPrecoBase()
		  , produto.getPrecoTarifado()
		);
	}
	
	public Produto transformeParaProduto(ProdutoEntity produtoEntity) {
		return new Produto(
			    produtoEntity.getId().toString()
			  , produtoEntity.getNome()
			  , Categoria.valueOf(produtoEntity.getCategoria().toUpperCase())
			  , produtoEntity.getPrecoBase()
		);		
	}
}
