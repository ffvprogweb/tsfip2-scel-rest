package com.fatec.scel.servico;

import org.springframework.http.HttpStatus;

public class CodigoDeRetorno {
	
	String mensagem;
	HttpStatus http;
	
	public CodigoDeRetorno(String m, HttpStatus http) {
		this.mensagem = "texot";
		this.http = http;
	}
	public CodigoDeRetorno() {
		
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public HttpStatus getHttp() {
		return http;
	}

	public void setHttp(HttpStatus http) {
		this.http = http;
	}

	@Override
	public String toString() {
		return "MensagemDeRetorno [mensagem=" + mensagem + ", http=" + http + "]";
	}
}
