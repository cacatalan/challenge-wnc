package com.acatalan.challengewnc.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.acatalan.challengewnc.model.dto.LastPrice;
import com.acatalan.challengewnc.model.entity.Price;
import com.acatalan.challengewnc.model.request.LastPriceByTimestampRequest;
import com.acatalan.challengewnc.model.request.TimestampAverageRequest;
import com.acatalan.challengewnc.model.response.TimestampAverageResponse;
import com.acatalan.challengewnc.service.LastPriceService;

import javassist.NotFoundException;

@RestController
public class LastPriceApi {
	
	@Autowired
	private LastPriceService service;
	
	/**
	 * get list of price in db
	 * @return
	 * @throws NotFoundException
	 */
	@RequestMapping(value="/prices", method=RequestMethod.GET)
	public ResponseEntity<List<Price>> findAll() throws NotFoundException {
		return ResponseEntity.ok(service.findAll());
	}
	
	/**
	 * find price by id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/prices/{id}", method=RequestMethod.GET)
	public ResponseEntity<LastPrice> findById(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok(service.findById(id));
	}
	
	/**
	 * find price by timestamp
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/prices_by_timestamp", method=RequestMethod.POST)
	public ResponseEntity<LastPrice> findByTimestamp(@Valid @RequestBody LastPriceByTimestampRequest request) throws Exception {
		return ResponseEntity.ok(service.findByTimestamp(request.getTimestamp()));
	}
	
	/**
	 * get max price in list of price, average of two timestamp, percentage difference of max price and averafe price of two timestamp.
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/timestamp_average", method=RequestMethod.POST)
	public ResponseEntity<TimestampAverageResponse> getTimestampAverage(@Valid @RequestBody TimestampAverageRequest request) throws Exception {
		return ResponseEntity.ok(service.getTimestampAverage(request));
	}
	
	/**
	 * get price in client and load price in db h2
	 * @return message successfully
	 */
	@RequestMapping(value="/load_prices", method=RequestMethod.POST)
	public ResponseEntity<String> loadPrices() {
		service.loadPrices();
		return ResponseEntity.ok("Loaded price list.");
	}
	
}
