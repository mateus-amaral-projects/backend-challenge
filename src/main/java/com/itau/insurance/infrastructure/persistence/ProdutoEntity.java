package com.itau.insurance.infrastructure.persistence;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRODUTO")
public class ProdutoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(nullable = false, unique = true)
	private UUID id;
	private String nome;
	private String categoria;
	private BigDecimal precoBase;
	private BigDecimal precoTarifado;
	
	public ProdutoEntity() {}
	
	public ProdutoEntity(UUID id, String nome, String categoria, BigDecimal precoBase, BigDecimal precoTarifado) {
		this.id = id;
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
