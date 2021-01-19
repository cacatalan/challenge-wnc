package com.acatalan.challengewnc.client;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.acatalan.challengewnc.exception.ApiException;
import com.acatalan.challengewnc.model.entity.Price;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LastPriceClient {

	private static final String API_PATH = "https://cex.io/api/last_price";
	private static final String URI_BTC_USD = "/BTC/USD";
	
	public Flux<String> loadPrices2() {
		return WebClient
				.create(API_PATH)
				.get()
				.uri(URI_BTC_USD)
				.retrieve()
				.bodyToFlux(String.class)
				.delaySubscription(Duration.ofSeconds(10))
				.repeat(3) //15 repeats call to client
				.log();
	}
	
	public Mono<List<Price>> loadPrices() {
		return WebClient
				.create(API_PATH)
				.get()
				.uri(URI_BTC_USD)
				.retrieve()
				.bodyToFlux(String.class)
//				.flatMap(response -> { response.setTimestamp(LocalDateTime.now().toString()); return response;})
				.map(response -> {
					Price responsePrice = null;
					try {
						responsePrice = new ObjectMapper().readValue(response, Price.class);
						responsePrice.setTimestamp(LocalDateTime.now().toString());
						return responsePrice;
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						new ApiException("Error en mapeo");
					}
					return responsePrice;
				})
				.delaySubscription(Duration.ofSeconds(10))
				.repeat(3) //15 repeats call to client
				.collectList();
	}

	public Mono<String> getLastPriceBTCAndUSD() throws JsonMappingException, JsonProcessingException {
		return WebClient
				.create(API_PATH)
				.get()
				.uri(URI_BTC_USD)
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse ->
	                Mono.error(new ApiException("Error Client: on getLastPriceBTCAndUSD."))
	            )
	            .onStatus(HttpStatus::is5xxServerError, clientResponse ->
	                Mono.error(new ApiException("Internal Server Error Client: on getLastPriceBTCAndUSD."))
	            )
				.bodyToMono(String.class)
	            .log("");
	}
	
}
