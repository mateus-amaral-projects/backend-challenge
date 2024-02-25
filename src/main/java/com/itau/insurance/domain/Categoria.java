package com.itau.insurance.domain;

import java.math.BigDecimal;

public enum Categoria {
	VIDA(BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.022), BigDecimal.ZERO),
	AUTO(BigDecimal.valueOf(0.055), BigDecimal.valueOf(0.04), BigDecimal.valueOf(0.01)),
	VIAGEM(BigDecimal.valueOf(0.02), BigDecimal.valueOf(0.04), BigDecimal.valueOf(0.01)),
	RESIDENCIAL(BigDecimal.valueOf(0.04), BigDecimal.ZERO, BigDecimal.valueOf(0.03)),
	PATRIMONIAL(BigDecimal.valueOf(0.05), BigDecimal.valueOf(0.03), BigDecimal.ZERO);
	
	private Categoria(BigDecimal iof, BigDecimal pis, BigDecimal cofins) {
		this.iof = iof;
		this.pis = pis;
		this.cofins = cofins;
	}
	
	private BigDecimal iof;
	private BigDecimal pis;
	private BigDecimal cofins;
	
	public BigDecimal getIof() {
		return iof;
	}
	
	public BigDecimal getPis() {
		return pis;
	}
	
	public BigDecimal getCofins() {
		return cofins;
	}
}
