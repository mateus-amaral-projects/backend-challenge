package com.itau.insurance.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class Produto {
	private UUID id;
	private String nome;
	private Categoria categoria;
	private BigDecimal precoBase;

	public Produto(String id, String nome, Categoria categoria, BigDecimal precoBase) {
		this.id = id != null ? UUID.fromString(id) : UUID.randomUUID();
		this.nome = nome;
		this.categoria = categoria;
		this.precoBase = precoBase;
	}

	public UUID getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public BigDecimal getPrecoBase() {
		return precoBase;
	}

	public BigDecimal getPrecoTarifado() {
		return precoBase.add(precoBase.multiply(getCategoria().getIof()))
			.add(precoBase.multiply(getCategoria().getPis())).add(precoBase.multiply(getCategoria().getCofins()))
				.setScale(2, RoundingMode.UP);
	}
}
