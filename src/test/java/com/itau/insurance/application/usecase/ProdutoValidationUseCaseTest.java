package com.itau.insurance.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.itau.insurance.application.gateway.ProdutoValidationGateway;
import com.itau.insurance.infrastructure.exceptions.ProdutoInvalidoException;

@SpringBootTest
class ProdutoValidationUseCaseTest {
	private final String mensagem1ProdutoInvalidoException = "Produto inválido. O nome não pode ser branco, nulo ou menor que 2 caracteres.";
	private final String mensagem2ProdutoInvalidoException = "Produto inválido. O preço base não pode ser menor ou igual a zero.";
	
	private String nomeValido, nomeNulo, nomeEmBranco, nomeMenorQueDoisCaracteres;
	
	private BigDecimal precoBaseValido, precoBaseMenorQueZero, precoBaseIgualAZero;
	
	@Mock
	private ProdutoValidationGateway produtoValidationGateway;
	
	@BeforeEach
	void setUp() {
		nomeValido = "Seguro de Vida Individual";
		nomeNulo = null;
		nomeEmBranco = "";
		nomeMenorQueDoisCaracteres = "S";
		precoBaseValido = BigDecimal.valueOf(100);
		precoBaseIgualAZero = BigDecimal.ZERO;
		precoBaseMenorQueZero = BigDecimal.valueOf(-1);
	}
	
	@Test
	@DisplayName("Deve validar o nome com sucesso.")
	void deveValidarNomeComSucesso() {
		ArgumentCaptor<String> nome = ArgumentCaptor.forClass(String.class);
		doNothing().when(produtoValidationGateway).validarNome(nome.capture());
		produtoValidationGateway.validarNome(nomeValido);
		assertEquals(nomeValido, nome.getValue());
	}
	
	@Test
	@DisplayName("Deve validar o nome lançando exceção.")
	void deveValidarNomeComProdutoInvalidoException() {
		Throwable exception = null;
//		nomeNulo		
		doThrow(new ProdutoInvalidoException(mensagem1ProdutoInvalidoException)).when(produtoValidationGateway).validarNome(nomeNulo);		
		exception = assertThrows(ProdutoInvalidoException.class, () -> produtoValidationGateway.validarNome(nomeNulo));
		assertTrue(exception.getMessage().contains(mensagem1ProdutoInvalidoException));
//		nomeEmBranco
		doThrow(new ProdutoInvalidoException(mensagem1ProdutoInvalidoException)).when(produtoValidationGateway).validarNome(nomeEmBranco);		
		exception = assertThrows(ProdutoInvalidoException.class, () -> produtoValidationGateway.validarNome(nomeEmBranco));
		assertTrue(exception.getMessage().contains(mensagem1ProdutoInvalidoException));
//		NomeMenorQueDoisCaracteres
		doThrow(new ProdutoInvalidoException(mensagem1ProdutoInvalidoException)).when(produtoValidationGateway).validarNome(nomeMenorQueDoisCaracteres);		
		exception = assertThrows(ProdutoInvalidoException.class, () -> produtoValidationGateway.validarNome(nomeMenorQueDoisCaracteres));
		assertTrue(exception.getMessage().contains(mensagem1ProdutoInvalidoException));
	}
	
	@Test
	@DisplayName("Deve validar o preço base com sucesso.")
	void deveValidarPrecoBaseComSucesso() {
		ArgumentCaptor<BigDecimal> precoBase = ArgumentCaptor.forClass(BigDecimal.class);
		doNothing().when(produtoValidationGateway).validarPrecoBase(precoBase.capture());
		produtoValidationGateway.validarPrecoBase(precoBaseValido);
		assertEquals(precoBaseValido, precoBase.getValue());
	}

	@Test
	@DisplayName("Deve validar o preço base lançando exceção.")
	void deveValidarPrecoBaseComProdutoInvalidoException() {
		Throwable exception = null;
//		nomeNulo		
		doThrow(new ProdutoInvalidoException(mensagem2ProdutoInvalidoException)).when(produtoValidationGateway).validarPrecoBase(precoBaseIgualAZero);		
		exception = assertThrows(ProdutoInvalidoException.class, () -> produtoValidationGateway.validarPrecoBase(precoBaseIgualAZero));
		assertTrue(exception.getMessage().contains(mensagem2ProdutoInvalidoException));
//		nomeEmBranco
		doThrow(new ProdutoInvalidoException(mensagem2ProdutoInvalidoException)).when(produtoValidationGateway).validarPrecoBase(precoBaseMenorQueZero);		
		exception = assertThrows(ProdutoInvalidoException.class, () -> produtoValidationGateway.validarPrecoBase(precoBaseMenorQueZero));
		assertTrue(exception.getMessage().contains(mensagem2ProdutoInvalidoException));
	}
}
