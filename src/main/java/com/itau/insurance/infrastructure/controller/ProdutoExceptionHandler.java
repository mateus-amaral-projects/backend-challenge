package com.itau.insurance.infrastructure.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.itau.insurance.infrastructure.exceptions.ProdutoCadastradoException;
import com.itau.insurance.infrastructure.exceptions.ProdutoInvalidoException;
import com.itau.insurance.infrastructure.exceptions.ProdutoNaoEncontradoException;
import com.itau.insurance.infrastructure.exceptions.TemplateExceptionMessage;

@ControllerAdvice
public class ProdutoExceptionHandler extends ResponseEntityExceptionHandler {

	private HttpHeaders headers() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return httpHeaders;
	}

	@ExceptionHandler(ProdutoCadastradoException.class)
	public ResponseEntity<Object> handleProdutoCadastradoException(ProdutoCadastradoException ex) {
		return new ResponseEntity<>(new TemplateExceptionMessage(HttpStatus.CONFLICT, ex.getMessage()), headers(),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ProdutoNaoEncontradoException.class)
	public ResponseEntity<Object> handleProdutoNaoEncontradoException(ProdutoNaoEncontradoException ex) {
		return new ResponseEntity<>(new TemplateExceptionMessage(HttpStatus.NOT_FOUND, ex.getMessage()), headers(),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ProdutoInvalidoException.class)
	public ResponseEntity<Object> handleIProdutoInvalidoException(ProdutoInvalidoException ex) {
		return new ResponseEntity<>(new TemplateExceptionMessage(HttpStatus.BAD_REQUEST, ex.getMessage()), headers(),
				HttpStatus.BAD_REQUEST);
	}
}
