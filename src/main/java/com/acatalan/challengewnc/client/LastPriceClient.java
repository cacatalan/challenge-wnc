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
					  .build();
	}
	
	public LastPrice getLastPrice() {
		LastPrice lastPrice = this.webClient
								.get()
								.uri("/last_price/BTC/USD")
								.retrieve()
								.bodyToMono(LastPrice.class)
								.block();
		
		return lastPrice;
	}

}
