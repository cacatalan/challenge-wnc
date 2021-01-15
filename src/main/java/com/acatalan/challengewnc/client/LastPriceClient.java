package com.acatalan.challengewnc.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.acatalan.challengewnc.dto.LastPrice;

@Service
public class LastPriceClient {

	private WebClient webClient;
	
	private WebClient.Builder builder = WebClient.builder();

	public LastPriceClient() {
		webClient = WebClient
					  .builder()
					    .baseUrl("https://cex.io/api")
//					    .defaultCookie("cookieKey", "cookieValue")
//					    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
//		                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
//					    .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
					  .build();
	}
	
	public LastPrice getLastPrice() {
		LastPrice lastPrice = this.webClient
								.get()
								.uri("https://cex.io/api/last_price/BTC/USD")
								//.accept(MediaType.APPLICATION_JSON)
								.retrieve()
								.bodyToMono(LastPrice.class)
								.block();
		
		return lastPrice;
	}

}
