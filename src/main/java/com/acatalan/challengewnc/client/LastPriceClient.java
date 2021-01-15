package com.acatalan.challengewnc.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import reactor.core.publisher.Mono;

@Service
public class LastPriceClient {
	
	public Mono<String> getByFirstCurrencyAndSecondCurrency() throws JsonMappingException, JsonProcessingException {
		return WebClient.create("https://cex.io/api/last_price/BTC")
								.get()
								.uri(uriBuilder -> uriBuilder.path("/USD").build())
								.retrieve()
								.bodyToMono(String.class);
	}

}
