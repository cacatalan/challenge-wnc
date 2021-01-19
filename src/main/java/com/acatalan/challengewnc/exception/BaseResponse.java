package com.acatalan.challengewnc.exception;

import lombok.Data;

@Data
public class BaseResponse {
	
	public BaseResponse(String message) {
        super();
        this.message = message;
    }
	
	private String message;
    
}
