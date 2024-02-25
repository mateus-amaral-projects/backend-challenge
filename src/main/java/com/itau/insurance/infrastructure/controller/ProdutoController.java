package com.itau.insurance.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.insurance.application.usecase.ProdutoUseCase;
import com.itau.insurance.application.usecase.ProdutoValidationUseCase;
import com.itau.insurance.domain.Produto;
import com.itau.insurance.infrastructure.exceptions.ProdutoCadastradoException;
import com.itau.insurance.infrastructure.exceptions.ProdutoInvalidoException;
import com.itau.insurance.infrastructure.exceptions.ProdutoNaoEncontradoException;

@RestController
@RequestMapping("/v1/seguros")
public class ProdutoController {
	private boolean novoRegistro;
	private ProdutoUseCase produtoUseCase;
	private ProdutoValidationUseCase produtoValidationUseCase;
	private ProdutoMapper produtoMapper;
	
	@Autowired
	public ProdutoController(ProdutoUseCase produtoUseCase, ProdutoValidationUseCase produtoValidationUseCase, ProdutoMapper produtoMapper) {
		this.produtoUseCase = produtoUseCase;
		this.produtoMapper = produtoMapper;
		this.produtoValidationUseCase = produtoValidationUseCase;
	}
	
	private void validarProduto(Produto produto, boolean novoRegistro) 
		throws ProdutoCadastradoException, ProdutoNaoEncontradoException, ProdutoInvalidoException {
		produtoValidationUseCase.validarNome(produto.getNome());
		produtoValidationUseCase.validarPrecoBase(produto.getPrecoBase());	
		if (novoRegistro) {
			if (produtoUseCase.existeProdutoPorNomeECategoria(produto))
				throw new ProdutoCadastradoException("Produto já cadastrado. Verifique os dados de entrada.");
		} else {
			if (!produtoUseCase.existeProdutoPorId(produto)) 
				throw new ProdutoNaoEncontradoException("Não é possível alterar um produto que não existe.");			
		}
	}
	
	@PostMapping("/produto")
	@Transactional
	public ResponseEntity<ProdutoResponse> criarProduto(@RequestBody ProdutoRequest produtoRequest)
		throws ProdutoCadastradoException, ProdutoInvalidoException  {
		novoRegistro = true;
		Produto produto = produtoMapper.transformeParaProduto(produtoRequest,novoRegistro);
		validarProduto(produto, novoRegistro);
		Produto produtoCriado = produtoUseCase.criarProduto(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoMapper.transformeParaProdutoResponse(produtoCriado));
	}

	@PutMapping("/produto")
	@Transactional
	public ResponseEntity<ProdutoResponse> alterarProduto(@RequestBody ProdutoRequest produtoRequest)
		throws ProdutoNaoEncontradoException, ProdutoInvalidoException {
		novoRegistro = false;
		Produto produto = produtoMapper.transformeParaProduto(produtoRequest,novoRegistro);
		validarProduto(produto, novoRegistro);
		Produto produtoAlterado = produtoUseCase.alterarProduto(produto);
		return ResponseEntity.status(HttpStatus.OK).body(produtoMapper.transformeParaProdutoResponse(produtoAlterado));
	}
}
