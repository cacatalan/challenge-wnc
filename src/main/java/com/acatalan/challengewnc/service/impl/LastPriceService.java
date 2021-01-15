package com.acatalan.challengewnc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acatalan.challengewnc.client.LastPriceClient;
import com.acatalan.challengewnc.dto.LastPrice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Service
public class LastPriceService {
	
	@Autowired
	LastPriceClient client;

	public LastPrice getByFirstCurrencyAndSecondCurrency() throws JsonMappingException, JsonProcessingException {

		Mono<String> responseJson = client.getByFirstCurrencyAndSecondCurrency();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		LastPrice lastPrice = mapper.readValue(responseJson.block().toString(), LastPrice.class);
		return lastPrice;
		
	}

}
