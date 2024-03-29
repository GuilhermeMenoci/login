package com.br.login.exception;

import lombok.Generated;

@Generated
public class NotAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = -3522355483151005629L;

	public NotAuthorizedException(String msg) {
		super(msg);
	}
}
