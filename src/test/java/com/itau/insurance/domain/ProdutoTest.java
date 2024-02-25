package com.itau.insurance.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProdutoTest {	

	@Mock
	private Produto produto1, produto2, produto3;
	
	@BeforeEach
	void setUp() {
		produto1 = new Produto(
			UUID.fromString("a0fe6d7d-b857-4e64-8e04-f4b167bb2a94").toString()
		  , "Seguro de Vida Individual"
		  , Categoria.VIDA
		  , BigDecimal.valueOf(100)
		);
		produto2 = new Produto(
			UUID.fromString("95f11f01-1c58-4189-af93-c47dfa08b59f").toString()
		  , "Seguro Veicular Padrão"
		  , Categoria.AUTO
		  , BigDecimal.valueOf(50)
		);
	}
	
	@Test
	@DisplayName("Deve criar um produto válido.")
	void deveCriarUmProdutoValido() {		
		assertAll(
			"Deve criar um produto válido"
		  , () -> assertEquals(UUID.fromString("a0fe6d7d-b857-4e64-8e04-f4b167bb2a94"),produto1.getId())
		  , () -> assertEquals("Seguro de Vida Individual",produto1.getNome())
		  , () -> assertEquals(Categoria.VIDA.name(),produto1.getCategoria().name())
		  , () -> assertEquals(BigDecimal.valueOf(100),produto1.getPrecoBase())
		  , () -> assertEquals(BigDecimal.valueOf(103.20).setScale(2, RoundingMode.UP),produto1.getPrecoTarifado())
		);		
	}

	@Test
	@DisplayName("Deve criar um produto inválido.")
	void deveCriarUmProdutoInvalido() {		
		assertAll(
			"Deve criar um produto inválido"
		  , () -> assertNotEquals(UUID.randomUUID(),produto2.getId())
		  , () -> assertNotEquals("Seguro de Vida Individual",produto2.getNome())
		  , () -> assertNotEquals(Categoria.VIDA.name(),produto2.getCategoria().name())
		  , () -> assertNotEquals(BigDecimal.valueOf(100),produto2.getPrecoBase())
		  , () -> assertNotEquals(BigDecimal.valueOf(103.20).setScale(2, RoundingMode.UP),produto2.getPrecoTarifado())
		);		
	}
	
	@Test
	@DisplayName("Deve criar um produto inválido lançando exceção.")
	void deveCriarUmProdutoInvalidoLancandoExcecao() {
		Throwable exception = null;
		try {
			produto3 = new Produto(
				UUID.fromString("366e2fe1-01df-4a19-a1dd-2ea91c118e65").toString()
			  , "Z"
			  , Categoria.valueOf("CELULAR")
			  , BigDecimal.valueOf(-1)
			);			
		} catch(IllegalArgumentException ex) {
			exception = ex;
		}
		final String mensagemEsperada = "No enum constant com.itau.insurance.domain.Categoria.CELULAR";
		final String mensagemAtual = exception.getMessage();
		assertTrue(mensagemAtual.contains(mensagemEsperada));
	}
}
