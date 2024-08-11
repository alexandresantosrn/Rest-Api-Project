package com.restapi.userdpt.arq;

public enum Messages {

	OK("Cadastro realizado com sucesso!"), 
	REMOVED("Dado removido com sucesso!"),
	UPDATED("Dados atualizados com sucesso!"), 
	ID_NOT_FOUND("Id not found: ");

	private final String message;

	private Messages(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
