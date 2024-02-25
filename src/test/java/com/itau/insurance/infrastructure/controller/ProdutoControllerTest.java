package com.itau.insurance.infrastructure.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.itau.insurance.application.usecase.ProdutoUseCase;
import com.itau.insurance.application.usecase.ProdutoValidationUseCase;
import com.itau.insurance.domain.Categoria;
import com.itau.insurance.domain.Produto;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProdutoControllerTest {	
	
	@Mock
	private Produto produto, produtoCriado;
	
	@Mock
	private ProdutoRequest produtoRequest1, produtoRequest2;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Mock
    private ProdutoUseCase produtoUseCase;
	
	@Mock
	private ProdutoValidationUseCase produtoValidationUseCase;
	
	@Mock
	private ProdutoMapper produtoMapper;
	
	@BeforeEach
	void setUp() {
		produto = Mockito.mock(Produto.class);
		produtoCriado = Mockito.mock(Produto.class);
		produtoRequest1 = Mockito.mock(ProdutoRequest.class);
		produtoRequest1 = new ProdutoRequest(
			null
		  , "Seguro de Vida Individual"
		  , Categoria.VIDA.name()
		  , BigDecimal.valueOf(100)
		);
		produtoMapper = Mockito.mock(ProdutoMapper.class);
		produtoUseCase = Mockito.mock(ProdutoUseCase.class);
	}
	
	@Test
	@DisplayName("Deve criar um novo produto.")
    void deveCriarUmProduto() throws Exception {
		Gson gson = new Gson();
		String jsonRequest = gson.toJson(produtoRequest1);
		System.out.println("CRIAR_PRODUTO -> JSONRequest: " + jsonRequest);
		ResultActions resultActions = this.mockMvc.perform(
			post("/v1/seguros/produto")
		   .contentType(MediaType.APPLICATION_JSON)
		   .content(jsonRequest)
		).andExpect(MockMvcResultMatchers.status().isCreated());
		String jsonResponse = resultActions.andReturn().getResponse().getContentAsString();
		JsonElement element = gson.fromJson(jsonResponse, JsonElement.class);
		JsonObject jsonResp = element.getAsJsonObject();
		produtoRequest1 = new ProdutoRequest(
			jsonResp.get("id").getAsString()
		  , jsonResp.get("nome").getAsString()
		  , jsonResp.get("categoria").getAsString()
		  , jsonResp.get("preco_base").getAsBigDecimal()
		);
		produto = new Produto(
			jsonResp.get("id").getAsString()
		  , jsonResp.get("nome").getAsString()
		  , Categoria.valueOf(jsonResp.get("categoria").getAsString()) 
		  , jsonResp.get("preco_base").getAsBigDecimal()
		);
		produtoCriado = new Produto(
			jsonResp.get("id").getAsString()
		  , jsonResp.get("nome").getAsString()
		  , Categoria.valueOf(jsonResp.get("categoria").getAsString()) 
		  , jsonResp.get("preco_base").getAsBigDecimal()
		);
		when(produtoMapper.transformeParaProduto(produtoRequest1, true)).thenReturn(produto);
		produto = produtoMapper.transformeParaProduto(produtoRequest1, true);
		when(produtoUseCase.criarProduto(produto)).thenReturn(produtoCriado);
		produtoCriado = produtoUseCase.criarProduto(produto);	
		System.out.println("CRIAR_PRODUTO -> JSONResponse: " + jsonResponse);
	}

	@Test
	@DisplayName("Deve alterar um produto.")
    void deveAlterarUmProduto() throws Exception {
//		Conteúdo no arquivo data.sql	
		System.out.println("ALTERAR_PRODUTO -> JSONRequest: {\"id\":\"6f0d84c1-b6d2-4ec4-9a59-59f4caa22c09\",\"nome\":\"Seguro de Vida Individual\",\"categoria\":\"VIDA\",\"precoBase\":100}" );
		produtoRequest2 = new ProdutoRequest(
			"6f0d84c1-b6d2-4ec4-9a59-59f4caa22c09"
		  , "Seguro de Vida Coletivo"
		  , Categoria.VIDA.name()
		  , BigDecimal.valueOf(550)
		);
		Gson gson = new Gson();
		String jsonRequest = gson.toJson(produtoRequest2);
		ResultActions resultActions = this.mockMvc.perform(
			put("/v1/seguros/produto")
		   .contentType(MediaType.APPLICATION_JSON)
		   .content(jsonRequest)
		).andExpect(MockMvcResultMatchers.status().isOk());
		String jsonResponse = resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("ALTERAR_PRODUTO -> JSONResponse: " + jsonResponse);
	}
}
