package com.itau.insurance.application.usecase;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.itau.insurance.application.gateway.ProdutoGateway;
import com.itau.insurance.domain.Categoria;
import com.itau.insurance.domain.Produto;
import com.itau.insurance.infrastructure.exceptions.ProdutoCadastradoException;
import com.itau.insurance.infrastructure.exceptions.ProdutoInvalidoException;
import com.itau.insurance.infrastructure.exceptions.ProdutoNaoEncontradoException;

import jakarta.persistence.PersistenceException;

@SpringBootTest
class ProdutoUseCaseTest {
	
	@Mock
	private Produto produto1, produto2, produto3, produto4;
	
	@Mock
	private ProdutoGateway produtoGateway;

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
		  , BigDecimal.ZERO
		);
		produto3 = new Produto(
			UUID.fromString("366e2fe1-01df-4a19-a1dd-2ea91c118e65").toString()
		  , "Seguro Viagem Exterior"
		  , Categoria.valueOf("VIAGEM")
		  , BigDecimal.valueOf(359,90)
		);
		produto4 = new Produto(
			UUID.fromString("8cfb5eb2-fd93-4322-bb74-c82f27c95a47").toString()
		  , "Seguro Residencial Básico"
		  , Categoria.valueOf("RESIDENCIAL")
		  , BigDecimal.valueOf(449.50)
		);
//		existeProdutoPorId		
		when(produtoGateway.existeProdutoPorId(produto1)).thenReturn(true);
		when(produtoGateway.existeProdutoPorId(produto2)).thenReturn(false);
//		existeProdutoPorNomeECategoria		
		when(produtoGateway.existeProdutoPorNomeECategoria(produto1)).thenReturn(true);
		when(produtoGateway.existeProdutoPorNomeECategoria(produto2)).thenReturn(false);
//		criarProduto
		when(produtoGateway.criarProduto(produto1)).thenReturn(produto1);
		when(produtoGateway.criarProduto(produto3)).thenThrow(new ProdutoCadastradoException("Produto já cadastrado. Verifique os dados de entrada."));		
		when(produtoGateway.criarProduto(produto2)).thenThrow(new ProdutoInvalidoException("Produto inválido. O preço base não pode ser menor ou igual a zero."));		
		when(produtoGateway.criarProduto(produto4)).thenThrow(new PersistenceException("Could not open connection"));
//		alterarProduto
		when(produtoGateway.alterarProduto(produto1)).thenReturn(produto2);
		when(produtoGateway.alterarProduto(produto3)).thenThrow(new ProdutoNaoEncontradoException("Não é possível alterar um produto que não existe."));		
		when(produtoGateway.alterarProduto(produto2)).thenThrow(new ProdutoInvalidoException("Produto inválido. O preço base não pode ser menor ou igual a zero."));		
		when(produtoGateway.alterarProduto(produto4)).thenThrow(new PersistenceException("Could not open connection"));
	}
	
	@Test
	@DisplayName("Deve existir produto por Id.")
	void deveExistirProdutoPorId() {
		boolean existeProdutoPorId = produtoGateway.existeProdutoPorId(produto1);
		assertTrue(existeProdutoPorId);
	}

	@Test
	@DisplayName("Não deve existir produto por Id.")
	void NaoDeveExistirProdutoPorId() {
		boolean existeProdutoPorId = produtoGateway.existeProdutoPorId(produto2);
		assertFalse(existeProdutoPorId);		
	}
	
	@Test
	@DisplayName("Deve existir produto por Nome e Categoria.")
	void deveExistirProdutoPorNomeECategoria() {
		boolean existeProdutoPorNomeECategoria = produtoGateway.existeProdutoPorNomeECategoria(produto1);
		assertTrue(existeProdutoPorNomeECategoria);
	}

	@Test
	@DisplayName("Não deve existir produto por Nome e Categoria.")
	void NaoDeveExistirProdutoPorNomeECategoria() {
		boolean existeProdutoPorNomeECategoria = produtoGateway.existeProdutoPorNomeECategoria(produto2);
		assertFalse(existeProdutoPorNomeECategoria);		
	}
	
	@Test
	@DisplayName("Deve criar o produto.")
	void deveCriarProduto() {		
		Produto produto = produtoGateway.criarProduto(produto1);
		assertAll(
		    () -> assertEquals(produto.getId(),produto1.getId())
		  , () -> assertEquals(produto.getNome(),produto1.getNome())
		  , () -> assertEquals(produto.getCategoria().name(),produto1.getCategoria().name())
		  , () -> assertEquals(produto.getPrecoBase(),produto1.getPrecoBase())
		  , () -> assertEquals(produto.getPrecoTarifado(),produto1.getPrecoTarifado())
		);		
	}
	
	@Test
	@DisplayName("Não deve criar o produto pois o mesmo já foi cadastrado.")
	void NaoDeveCriarProdutoPorProdutoCadastradoException() {		
		Throwable exception = assertThrows(ProdutoCadastradoException.class, () -> produtoGateway.criarProduto(produto3));	
		final String mensagemEsperada = "Produto já cadastrado. Verifique os dados de entrada.";
		final String mensagemAtual = exception.getMessage();
		assertTrue(mensagemAtual.contains(mensagemEsperada));
	}

	@Test
	@DisplayName("Não deve criar o produto pois o mesmo está inválido.")
	void NaoDeveCriarProdutoPorProdutoInvalidoException() {		
		Throwable exception = assertThrows(ProdutoInvalidoException.class, () -> produtoGateway.criarProduto(produto2));	
		final String mensagemEsperada = "Produto inválido. O preço base não pode ser menor ou igual a zero.";
		final String mensagemAtual = exception.getMessage();
		assertTrue(mensagemAtual.contains(mensagemEsperada));
	}

	@Test
	@DisplayName("Não deve criar o produto por problemas ao persistir.")
	void NaoDeveCriarProdutoPorPersistenceException() {		
		Throwable exception = assertThrows(PersistenceException.class, () -> produtoGateway.criarProduto(produto4));	
		final String mensagemEsperada = "Could not open connection";
		final String mensagemAtual = exception.getMessage();
		assertTrue(mensagemAtual.contains(mensagemEsperada));
	}

	@Test
	@DisplayName("Deve alterar o produto.")
	void deveAlterarProduto() {		
		Produto produto = produtoGateway.alterarProduto(produto1);
		assertAll(
		    () -> assertNotEquals(produto.getId(),produto1.getId())
		  , () -> assertNotEquals(produto.getNome(),produto1.getNome())
		  , () -> assertNotEquals(produto.getCategoria().name(),produto1.getCategoria().name())
		  , () -> assertNotEquals(produto.getPrecoBase(),produto1.getPrecoBase())
		  , () -> assertNotEquals(produto.getPrecoTarifado(),produto1.getPrecoTarifado())
		);		
	}
	
	@Test
	@DisplayName("Não deve alterar o produto pois o mesmo já foi cadastrado.")
	void NaoDeveAlterarProdutoPorProdutoCadastradoException() {		
		Throwable exception = assertThrows(ProdutoNaoEncontradoException.class, () -> produtoGateway.alterarProduto(produto3));	
		final String mensagemEsperada = "Não é possível alterar um produto que não existe.";
		final String mensagemAtual = exception.getMessage();
		assertTrue(mensagemAtual.contains(mensagemEsperada));
	}

	@Test
	@DisplayName("Não deve alterar o produto pois o mesmo está inválido.")
	void NaoDeveAlterarProdutoPorProdutoInvalidoException() {		
		Throwable exception = assertThrows(ProdutoInvalidoException.class, () -> produtoGateway.alterarProduto(produto2));	
		final String mensagemEsperada = "Produto inválido. O preço base não pode ser menor ou igual a zero.";
		final String mensagemAtual = exception.getMessage();
		assertTrue(mensagemAtual.contains(mensagemEsperada));
	}

	@Test
	@DisplayName("Não deve alterar o produto por problemas ao persistir.")
	void NaoDeveAlterarProdutoPorPersistenceException() {		
		Throwable exception = assertThrows(PersistenceException.class, () -> produtoGateway.alterarProduto(produto4));	
		final String mensagemEsperada = "Could not open connection";
		final String mensagemAtual = exception.getMessage();
		assertTrue(mensagemAtual.contains(mensagemEsperada));
	}
}