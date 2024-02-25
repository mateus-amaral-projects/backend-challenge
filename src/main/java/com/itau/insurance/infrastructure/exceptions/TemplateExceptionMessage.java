package com.itau.insurance.infrastructure.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TemplateExceptionMessage{
	private HttpStatus status;
	private String mensagem;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	private LocalDateTime dataHora;

	private TemplateExceptionMessage() {
		dataHora = LocalDateTime.now();
	}

	public TemplateExceptionMessage(String mensagem) {
		this();
		this.mensagem = mensagem;
	}
	
	public TemplateExceptionMessage(HttpStatus status, String mensagem) {
		this();
		this.status = status;
		this.mensagem = mensagem;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	
	public LocalDateTime getDataHora() {
		return dataHora;
	}	
}
