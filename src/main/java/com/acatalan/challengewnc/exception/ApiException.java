package com.acatalan.challengewnc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiException extends Exception {
	
	public ApiException(String message) {
		super();
		this.message = message;
	}
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String message;

}
