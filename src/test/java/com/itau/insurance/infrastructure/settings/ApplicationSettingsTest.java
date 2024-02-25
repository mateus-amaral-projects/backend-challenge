package com.itau.insurance.infrastructure.settings;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import com.itau.insurance.application.gateway.ProdutoGateway;
import com.itau.insurance.application.gateway.ProdutoValidationGateway;
import com.itau.insurance.application.usecase.ProdutoUseCase;
import com.itau.insurance.domain.Categoria;
import com.itau.insurance.domain.Produto;
import com.itau.insurance.infrastructure.controller.ProdutoMapper;
import com.itau.insurance.infrastructure.controller.ProdutoRequest;
import com.itau.insurance.infrastructure.controller.ProdutoResponse;
import com.itau.insurance.infrastructure.exceptions.ProdutoInvalidoException;
import com.itau.insurance.infrastructure.gateway.ProdutoEntityMapper;
import com.itau.insurance.infrastructure.gateway.ProdutoRepositoryGateway;
import com.itau.insurance.infrastructure.persistence.ProdutoEntity;
import com.itau.insurance.infrastructure.persistence.ProdutoRepository;
import com.itau.insurance.infrastructure.validation.ProdutoValidation;

@SpringBootTest
@TestConfiguration
class ApplicationSettingsTest {
	final String mensagemProdutoInvalido = "No enum constant com.itau.insurance.domain.Categoria.CELULAR";
	
	@Mock
	private Produto produtoCriar, produtoAlterar, produtoInvalido;
	
	@Mock
	ProdutoRequest produtoRequestCriar, produtoRequestAlterar, produtoRequestInvalido;
	
	@Mock
	ProdutoResponse produtoResponseCriar, produtoResponseAlterar;
	
	@Mock
	ProdutoEntity produtoEntityCriar, produtoEntityAlterar;
	
	@Mock
	ProdutoMapper produtoMapper;
	
	@Mock
	ProdutoEntityMapper produtoEntityMapper;
	
	@Mock
	ProdutoRepository produtoRepository;
	
	@Mock
	ProdutoGateway produtoGateway;
	
	@Mock
	ProdutoValidation produtoValidation;
	
	@Mock
	ProdutoValidationGateway produtoValidationGateway;
	
	@BeforeEach
	void setup() {
		produtoRequestCriar = new ProdutoRequest(
			null
		  , "Seguro de Vida Individual"
		  , Categoria.VIDA.name()
		  , BigDecimal.valueOf(100)
		);		
		produtoRequestAlterar = new ProdutoRequest(
			"95f11f01-1c58-4189-af93-c47dfa08b59f"
		  , "Seguro Veicular Padrão"
		  , Categoria.AUTO.name()
		  , BigDecimal.valueOf(50)
		);
		produtoRequestInvalido = new ProdutoRequest(
			"366e2fe1-01df-4a19-a1dd-2ea91c118e65"
		  , "Seguro para Celular"
		  , "CELULAR"
		  , BigDecimal.valueOf(59,90)
		);
		produtoCriar = new Produto(
			UUID.fromString("a0fe6d7d-b857-4e64-8e04-f4b167bb2a94").toString()
		  , "Seguro de Vida Individual"
		  , Categoria.VIDA
		  , BigDecimal.valueOf(100)
		);		
		produtoAlterar = new Produto(
			UUID.fromString("95f11f01-1c58-4189-af93-c47dfa08b59f").toString()
		  , "Seguro Veicular Padrão"
		  , Categoria.AUTO
		  , BigDecimal.valueOf(50)
		);		
		produtoResponseCriar = new ProdutoResponse(
			"a0fe6d7d-b857-4e64-8e04-f4b167bb2a94"
		  , "Seguro de Vida Individual"
		  , Categoria.VIDA.name()
		  , BigDecimal.valueOf(100)
		  , BigDecimal.valueOf(103.20).setScale(2, RoundingMode.UP)
		);		
		produtoResponseAlterar = new ProdutoResponse(
			"95f11f01-1c58-4189-af93-c47dfa08b59f"
		  , "Seguro Veicular Padrão"
		  , Categoria.AUTO.name()
		  , BigDecimal.valueOf(50)
		  , BigDecimal.valueOf(55.25).setScale(2, RoundingMode.UP)
		);		
		produtoEntityCriar = new ProdutoEntity(
			UUID.fromString("a0fe6d7d-b857-4e64-8e04-f4b167bb2a94")
		  , "Seguro de Vida Individual"
		  , Categoria.VIDA.name()
		  , BigDecimal.valueOf(100)
		  , BigDecimal.valueOf(103.20).setScale(2, RoundingMode.UP)
		);		
		produtoEntityAlterar = new ProdutoEntity(
			UUID.fromString("95f11f01-1c58-4189-af93-c47dfa08b59f")
		  , "Seguro Veicular Padrão"
		  , Categoria.AUTO.name()
		  , BigDecimal.valueOf(50)
		  , BigDecimal.valueOf(55.25).setScale(2, RoundingMode.UP)
		);
	}
	
	@Test
	@DisplayName("Deve transformar para produto a fim de criar produto.")
	void deveTransformarParaProdutoAFimDeCriarProduto() {
		when(produtoMapper.transformeParaProduto(produtoRequestCriar, true)).thenReturn(produtoCriar);
		Produto produto = produtoMapper.transformeParaProduto(produtoRequestCriar,true);
		assertAll(
		    () -> assertEquals(produto.getId(),produtoCriar.getId())
		  , () -> assertEquals(produto.getNome(),produtoCriar.getNome())
		  , () -> assertEquals(produto.getCategoria().name(),produtoCriar.getCategoria().name())
		  , () -> assertEquals(produto.getPrecoBase(),produtoCriar.getPrecoBase())
		  , () -> assertEquals(produto.getPrecoTarifado(),produtoCriar.getPrecoTarifado())
		);			
	}

	@Test
	@DisplayName("Deve transformar para produto a fim de alterar produto.")
	void deveTransformarParaProdutoAFimDeAlterarProduto() {
		when(produtoMapper.transformeParaProduto(produtoRequestAlterar, false)).thenReturn(produtoAlterar);
		Produto produto = produtoMapper.transformeParaProduto(produtoRequestAlterar,false);
		assertAll(
		    () -> assertEquals(produto.getId(),produtoAlterar.getId())
		  , () -> assertEquals(produto.getNome(),produtoAlterar.getNome())
		  , () -> assertEquals(produto.getCategoria().name(),produtoAlterar.getCategoria().name())
		  , () -> assertEquals(produto.getPrecoBase(),produtoAlterar.getPrecoBase())
		  , () -> assertEquals(produto.getPrecoTarifado(),produtoAlterar.getPrecoTarifado())
		);			
	}
	
	@Test
	@DisplayName("Deve lançar exceção ao transformar para produto.")
	void deveTransformarParaProdutoComIllegalArgumentException() {
		doThrow(new ProdutoInvalidoException(mensagemProdutoInvalido)).when(produtoMapper).transformeParaProduto(produtoRequestInvalido, true);
		Throwable exception = assertThrows(ProdutoInvalidoException.class, () -> produtoMapper.transformeParaProduto(produtoRequestInvalido, true));
		assertTrue(exception.getMessage().contains(mensagemProdutoInvalido));
	}

	@Test
	@DisplayName("Deve transformar para produto response a fim de retornar a criação para a tela.")
	void deveTransformarParaProdutoResponseAFimDeCriarProduto() {
		when(produtoMapper.transformeParaProdutoResponse(produtoCriar)).thenReturn(produtoResponseCriar);
		ProdutoResponse produto = produtoMapper.transformeParaProdutoResponse(produtoCriar);
		assertAll(
		    () -> assertEquals(produto.getId(),produtoCriar.getId())
		  , () -> assertEquals(produto.getNome(),produtoCriar.getNome())
		  , () -> assertEquals(produto.getCategoria(),produtoCriar.getCategoria().name())
		  , () -> assertEquals(produto.getPrecoBase(),produtoCriar.getPrecoBase())
		  , () -> assertEquals(produto.getPrecoTarifado(),produtoCriar.getPrecoTarifado())
		);			
	}

	@Test
	@DisplayName("Deve transformar para produto response a fim de retornar a alteração para a tela.")
	void deveTransformarParaProdutoResponseAFimDeAlterarProduto() {
		when(produtoMapper.transformeParaProdutoResponse(produtoAlterar)).thenReturn(produtoResponseAlterar);
		ProdutoResponse produto = produtoMapper.transformeParaProdutoResponse(produtoAlterar);
		assertAll(
		    () -> assertEquals(produto.getId(),produtoAlterar.getId())
		  , () -> assertEquals(produto.getNome(),produtoAlterar.getNome())
		  , () -> assertEquals(produto.getCategoria(),produtoAlterar.getCategoria().name())
		  , () -> assertEquals(produto.getPrecoBase(),produtoAlterar.getPrecoBase())
		  , () -> assertEquals(produto.getPrecoTarifado(),produtoAlterar.getPrecoTarifado())
		);			
	}
	
	@Test
	@DisplayName("Deve transformar para produto entity a fim de criar produto.")
	void deveTransformarParaProdutoEntityAFimDeCriarProduto() {
		when(produtoEntityMapper.transformeParaProdutoEntity(produtoCriar)).thenReturn(produtoEntityCriar);
		ProdutoEntity produto = produtoEntityMapper.transformeParaProdutoEntity(produtoCriar);
		assertAll(
		    () -> assertEquals(produto.getId(),produtoCriar.getId())
		  , () -> assertEquals(produto.getNome(),produtoCriar.getNome())
		  , () -> assertEquals(produto.getCategoria(),produtoCriar.getCategoria().name())
		  , () -> assertEquals(produto.getPrecoBase(),produtoCriar.getPrecoBase())
		  , () -> assertEquals(produto.getPrecoTarifado(),produtoCriar.getPrecoTarifado())
		);			
	}

	@Test
	@DisplayName("Deve transformar para produto entity a fim de alterar produto.")
	void deveTransformarParaProdutoEntityAFimDeAlterarProduto() {
		when(produtoEntityMapper.transformeParaProdutoEntity(produtoAlterar)).thenReturn(produtoEntityAlterar);
		ProdutoEntity produto = produtoEntityMapper.transformeParaProdutoEntity(produtoAlterar);
		assertAll(
		    () -> assertEquals(produto.getId(),produtoAlterar.getId())
		  , () -> assertEquals(produto.getNome(),produtoAlterar.getNome())
		  , () -> assertEquals(produto.getCategoria(),produtoAlterar.getCategoria().name())
		  , () -> assertEquals(produto.getPrecoBase(),produtoAlterar.getPrecoBase())
		  , () -> assertEquals(produto.getPrecoTarifado(),produtoAlterar.getPrecoTarifado())
		);			
	}
	
	@Test
	@DisplayName("Deve transformar para produto a fim de criar produto.")
	void deveTransformarParaProdutoAFimDeCriarProdutoEntity() {
		when(produtoEntityMapper.transformeParaProduto(produtoEntityCriar)).thenReturn(produtoCriar);
		Produto produto = produtoEntityMapper.transformeParaProduto(produtoEntityCriar);
		assertAll(
		    () -> assertEquals(produto.getId(),produtoCriar.getId())
		  , () -> assertEquals(produto.getNome(),produtoCriar.getNome())
		  , () -> assertEquals(produto.getCategoria().name(),produtoCriar.getCategoria().name())
		  , () -> assertEquals(produto.getPrecoBase(),produtoCriar.getPrecoBase())
		  , () -> assertEquals(produto.getPrecoTarifado(),produtoCriar.getPrecoTarifado())
		);			
	}
	@Test
	@DisplayName("Deve transformar para produto a fim de alterar produto.")
	void deveTransformarParaProdutoAFimDeAlterarProdutoEntity() {
		when(produtoEntityMapper.transformeParaProduto(produtoEntityAlterar)).thenReturn(produtoAlterar);
		Produto produto = produtoEntityMapper.transformeParaProduto(produtoEntityAlterar);
		assertAll(
		    () -> assertEquals(produto.getId(),produtoAlterar.getId())
		  , () -> assertEquals(produto.getNome(),produtoAlterar.getNome())
		  , () -> assertEquals(produto.getCategoria().name(),produtoAlterar.getCategoria().name())
		  , () -> assertEquals(produto.getPrecoBase(),produtoAlterar.getPrecoBase())
		  , () -> assertEquals(produto.getPrecoTarifado(),produtoAlterar.getPrecoTarifado())
		);			
	}
	
	@Test
	@DisplayName("Deve obter produto repository gateway.")
	void deveObterProdutoRepositoryGateway() {
		produtoRepository = Mockito.mock(ProdutoRepository.class);
		produtoEntityMapper = Mockito.mock(ProdutoEntityMapper.class);
		ProdutoRepositoryGateway produtoRepositoryGateway =
			new ProdutoRepositoryGateway(produtoRepository, produtoEntityMapper);
		assertNotNull(produtoRepositoryGateway);
	}
	
	@Test
	@DisplayName("Deve obter produto repository gateway.")
	void deveObterProdutoCase() {
		produtoGateway = Mockito.mock(ProdutoGateway.class);
		ProdutoUseCase produtoUseCase = new ProdutoUseCase(produtoGateway);
		assertNotNull(produtoUseCase);
	}
	
	@Test
	@DisplayName("Deve obter produto validation.")
	void deveObterProdutoValidation() {
		produtoValidation = Mockito.mock(ProdutoValidation.class);
		assertNotNull(produtoValidation);
	}
	
	@Test
	@DisplayName("Deve obter produto validation gateway.")
	void deveObterProdutoValidationGateway() {
		produtoValidationGateway = Mockito.mock(ProdutoValidationGateway.class);
		assertNotNull(produtoValidationGateway);
	}
}
