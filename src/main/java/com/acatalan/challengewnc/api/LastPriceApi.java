package com.acatalan.challengewnc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.acatalan.challengewnc.dto.LastPrice;
import com.acatalan.challengewnc.service.impl.LastPriceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
public class LastPriceApi {
	
	@Autowired
	private LastPriceService service;
	
	@RequestMapping(value="/lastpriceDefault", method=RequestMethod.GET)
	public LastPrice getDefault() {
		return new LastPrice("100.05", "BTC", "USD");
	}
	
	@RequestMapping(value="/lastprice", method=RequestMethod.GET)
	public LastPrice getByFirstCurrencyAndSecondCurrency() throws JsonMappingException, JsonProcessingException {
		return service.getByFirstCurrencyAndSecondCurrency();
	}
	
}
