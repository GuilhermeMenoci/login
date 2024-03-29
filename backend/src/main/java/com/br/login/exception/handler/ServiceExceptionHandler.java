package com.br.login.exception.handler;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.br.login.exception.ExceptionResponseVO;
import com.br.login.exception.InvalidParameterException;
import com.br.login.exception.LoginRegistered;
import com.br.login.exception.NotAuthorizedException;
import com.br.login.exception.NotFoundException;

import lombok.Generated;

@Generated
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String HEADER_MESSAGE = "mensagem";

	private static final String TITLE_PARAMETROS_INVALIDOS = "Invalid parameter";
	private static final String TITLE_DADOS_JA_CADASTRADOS = "Login registered";
	private static final String TITLE_NAO_AUTORIZADO = "Não autorizado";
	private static final String TITLE_NAO_ENCONTRADO = "Register not found";

	private static final String TYPE_VALIDACAO_PARAMETROS = "Parameter validation";
	private static final String TYPE_DADOS_JA_CADASTRADOS = "Login already registered";
	private static final String TYPE_NAO_AUTORIZADO = "Não autorizado";
	private static final String TYPE_NAO_ENCONTRADO = "Register not found";
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNaoEncontradoException(NotFoundException e, ServletWebRequest request) {
		logger.warn(e.getMessage());

		ExceptionResponseVO bodyExceptionResponse = criarExceptionResponse(TITLE_NAO_ENCONTRADO,
				TYPE_NAO_ENCONTRADO, Arrays.asList(e.getMessage()), request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, bodyExceptionResponse, header, HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<Object> handleParametroInvalidoException(InvalidParameterException e, ServletWebRequest request) {
		logger.warn(e.getMessage());

		ExceptionResponseVO bodyExceptionResponse = criarExceptionResponse(TITLE_PARAMETROS_INVALIDOS,
				TYPE_VALIDACAO_PARAMETROS, Arrays.asList(e.getMessage()), request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, bodyExceptionResponse, header, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(LoginRegistered.class)
	public ResponseEntity<Object> handleDadosJaCadastradosException(LoginRegistered e, ServletWebRequest request) {
		logger.warn(e.getMessage());

		ExceptionResponseVO bodyExceptionResponse = criarExceptionResponse(TITLE_DADOS_JA_CADASTRADOS,
				TYPE_DADOS_JA_CADASTRADOS, Arrays.asList(e.getMessage()), request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, bodyExceptionResponse, header, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(NotAuthorizedException.class)
	public ResponseEntity<Object> handleNaoAutorizadoException(NotAuthorizedException e, ServletWebRequest request) {
		logger.warn(e.getMessage());

		ExceptionResponseVO bodyExceptionResponse = criarExceptionResponse(TITLE_NAO_AUTORIZADO,
				TYPE_NAO_AUTORIZADO, Arrays.asList(e.getMessage()), request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, bodyExceptionResponse, header, HttpStatus.UNAUTHORIZED, request);
	}
	
	private ExceptionResponseVO criarExceptionResponse(String title, String type, List<String> detail, String instance) {
		return ExceptionResponseVO.builder()
				.detail(detail)
				.instance(instance)
				.title(title)
				.type(type)
				.build();

	}
	
}
