package com.example.test.exception;

public class InventaireCustomException extends RuntimeException {
	private static final long serialVersionUID = -291211739734090347L;
	public InventaireCustomException(String message) {
		super(message);
	}

}
