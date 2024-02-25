package com.itau.insurance.infrastructure.controller;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProdutoResponse {
	private UUID id;
	private String nome;
	private String categoria;
	@JsonProperty("preco_base")
	private BigDecimal precoBase;
	@JsonProperty("preco_tarifado")
	private BigDecimal precoTarifado;

	public ProdutoResponse(String id, String nome, String categoria, BigDecimal precoBase, BigDecimal precoTarifado) {
		this.id = id != null ? UUID.fromString(id) : UUID.randomUUID();
		this.nome = nome;
		this.categoria = categoria;
		this.precoBase = precoBase;
		this.precoTarifado = precoTarifado;
	}

	public UUID getId() {
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
	
	public BigDecimal getPrecoTarifado() {
		return precoTarifado;
	}
}
