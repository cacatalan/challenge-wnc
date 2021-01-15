package com.acatalan.challengewnc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.acatalan.challengewnc.client.LastPriceClient;
import com.acatalan.challengewnc.dto.LastPrice;

@RestController
public class LastPriceApi {
	
	@Autowired
	private LastPriceClient client;
	
	@RequestMapping(value="/lastprice", method=RequestMethod.GET)
	public LastPrice getByFirstCurrencyAndSecondCurrency() {
		return new LastPrice("100.05", "BTC", "USD");
	}
	
	@RequestMapping(value="/lastprice2", method=RequestMethod.GET)
	public LastPrice getByFirstCurrencyAndSecondCurrency2() {
		return client.getLastPrice();
	}
	
}
