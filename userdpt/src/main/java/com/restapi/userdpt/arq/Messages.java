package com.restapi.userdpt.arq;

public enum Messages {

	SUCESS("Cadastro realizado com sucesso!"), REMOTION("Dado removido com sucesso!"),
	UPDATE("Dados atualizados com sucesso!");

	private final String message;

	private Messages(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
