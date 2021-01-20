package com.acatalan.challengewnc.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimestampAverageResponse {

	private String average;
	private String difference;
	
	@JsonProperty("max_value")
	private String maxValue;
	
}
