package com.itau.insurance.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoriaTest {
	
	@Test
	@DisplayName("Deve consumir uma categoria válida.")
	void deveConsumirUmaCategoriaValida() {
		assertAll(
			"Deve consumir uma categoria válida"
		  , () -> assertEquals("VIDA", Categoria.VIDA.name())
		  , () -> assertEquals(BigDecimal.valueOf(0.01),Categoria.VIDA.getIof())
		  , () -> assertEquals(BigDecimal.valueOf(0.022),Categoria.VIDA.getPis())
		  , () -> assertEquals(BigDecimal.ZERO,Categoria.VIDA.getCofins())
		);				
	}
	
	@Test
	@DisplayName("Deve consumir uma categoria inválida.")
	void deveConsumirUmaCategoriaInvalida() {
		assertAll(
			"Deve consumir uma categoria inválida"
		  , () -> assertNotEquals("VIDA", Categoria.PATRIMONIAL.name())
		  , () -> assertNotEquals(BigDecimal.valueOf(0.01),Categoria.PATRIMONIAL.getIof())
		  , () -> assertNotEquals(BigDecimal.valueOf(0.022),Categoria.PATRIMONIAL.getPis())
		  , () -> assertNotEquals(BigDecimal.valueOf(0.05),Categoria.PATRIMONIAL.getCofins())
		);				
	}

	@Test
	@DisplayName("Deve consumir uma categoria inválida lançando exceção.")
	void deveConsumirUmaCategoriaInvalidaLancandoExcecao() {
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> Categoria.valueOf("CELULAR"));	
		final String mensagemEsperada = "No enum constant com.itau.insurance.domain.Categoria.CELULAR";
		final String mensagemAtual = exception.getMessage();
		assertTrue(mensagemAtual.contains(mensagemEsperada));
	}
}
