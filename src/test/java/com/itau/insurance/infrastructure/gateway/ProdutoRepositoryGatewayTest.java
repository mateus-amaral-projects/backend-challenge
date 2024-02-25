package com.itau.insurance.infrastructure.gateway;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.itau.insurance.domain.Categoria;
import com.itau.insurance.domain.Produto;
import com.itau.insurance.infrastructure.persistence.ProdutoRepository;

@SpringBootTest
@ActiveProfiles("test")
class ProdutoRepositoryGatewayTest {
	@Mock
	private Produto produto1, produto2;
	
	@Autowired
    private ProdutoRepositoryGateway produtoRepositoryGateway;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoEntityMapper produtoEntityMapper;
	
	@BeforeEach
	void setUp() {
		produto1 = new Produto(
			UUID.fromString("a0fe6d7d-b857-4e64-8e04-f4b167bb2a94").toString()
		  , "Seguro de Vida Individual"
		  , Categoria.VIDA
		  , BigDecimal.valueOf(100)
		);
		produtoRepositoryGateway = new ProdutoRepositoryGateway(produtoRepository,produtoEntityMapper);
	}
	
	@Test
	@DisplayName("Deve criar um novo produto.")
    void deveCriarUmProduto() {
		Produto produto = produtoRepositoryGateway.criarProduto(produto1);
		assertNotNull(produto.getId());
		assertAll(
		    () -> assertEquals(produto.getNome(),produto1.getNome())
		  , () -> assertEquals(produto.getCategoria().name(),produto1.getCategoria().name())
		  , () -> assertEquals(produto.getPrecoBase(),produto1.getPrecoBase())
		  , () -> assertEquals(produto.getPrecoTarifado(),produto1.getPrecoTarifado())
		);
		this.produto1 = produto;
	}
	
	@Test
	@DisplayName("Deve verificar se o produto existe pelo Id.")
    void deveVerificarSeOProdutoExistePorId() {
		deveCriarUmProduto();
		boolean existeProdutoPorId = produtoRepositoryGateway.existeProdutoPorId(produto1);
		assertTrue(existeProdutoPorId);
	}

	@Test
	@DisplayName("Deve verificar o produto existe pelo Nome e Categoria.")
    void deveVerificarSeOProdutoExistePorNomeECategoria() {
		deveCriarUmProduto();
		boolean existeProdutoPorNomeECategoria = produtoRepositoryGateway.existeProdutoPorNomeECategoria(produto1);
		assertTrue(existeProdutoPorNomeECategoria);
	}

	@Test
	@DisplayName("Deve alterar um produto.")
    void deveAlterarUmProduto() {
		deveCriarUmProduto();
		produto2 = produto1;
		produto1 = new Produto(
			produto2.getId().toString()
		  , "Seguro de Vida Coletivo"
		  , Categoria.VIDA
		  , BigDecimal.valueOf(550)
		);	
		Produto produto = produtoRepositoryGateway.alterarProduto(produto1);
		assertAll(
		    () -> assertEquals(produto.getId(),produto1.getId())
		  , () -> assertNotEquals(produto.getNome(),produto2.getNome())
		  , () -> assertEquals(produto.getCategoria().name(),produto1.getCategoria().name())
		  , () -> assertNotEquals(produto.getPrecoBase(),produto2.getPrecoBase())
		  , () -> assertNotEquals(produto.getPrecoTarifado(),produto2.getPrecoTarifado())
		);
	}
}
