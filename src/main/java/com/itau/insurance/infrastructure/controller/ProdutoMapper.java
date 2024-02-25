package com.itau.insurance.infrastructure.controller;

import com.itau.insurance.domain.Categoria;
import com.itau.insurance.domain.Produto;
import com.itau.insurance.infrastructure.exceptions.ProdutoInvalidoException;

public class ProdutoMapper {
	public Produto transformeParaProduto(ProdutoRequest produtoRequest, boolean criarNovoRegistro) {
		try {
			return new Produto(
				criarNovoRegistro ? null : produtoRequest.getId()
			  , produtoRequest.getNome()
			  , Categoria.valueOf(produtoRequest.getCategoria())
			  , produtoRequest.getPrecoBase()
			);
		} catch(IllegalArgumentException ex) {
			throw new ProdutoInvalidoException(
				"Produto inválido. As Categorias aceitas são: VIDA, AUTO, VIAGEM, RESIDENCIAL e PATRIMONIAL.");
		}
	}
	
	public ProdutoResponse transformeParaProdutoResponse(Produto produto) {
		return new ProdutoResponse(
			produto.getId().toString()
		  , produto.getNome()
		  , produto.getCategoria().name()
		  , produto.getPrecoBase()
		  , produto.getPrecoTarifado()
		);				
	}
}
