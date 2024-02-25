package com.itau.insurance.infrastructure.controller;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProdutoRequest {
	private String id;
	private String nome;
	private String categoria;
	@JsonProperty("preco_base")
	private BigDecimal precoBase;
	
	public ProdutoRequest(String id, String nome, String categoria, BigDecimal precoBase) {
		this.id = id;
		this.nome = nome;
		this.categoria = categoria.toUpperCase();
		this.precoBase = precoBase;
	}
	
	public String getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public BigDecimal getPrecoBase() {
		return precoBase;
	}
}
