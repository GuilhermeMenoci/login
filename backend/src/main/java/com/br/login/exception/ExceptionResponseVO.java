package com.br.login.exception;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ExceptionResponseVO implements Serializable {

	private static final long serialVersionUID = 7289176671522499397L;
	
	private String type;
	private String title;
	private List<String> detail;
	private String instance;
}
