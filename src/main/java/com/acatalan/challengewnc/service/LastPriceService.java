package com.acatalan.challengewnc.service;

import java.util.List;

import com.acatalan.challengewnc.model.dto.LastPrice;
import com.acatalan.challengewnc.model.entity.Price;
import com.acatalan.challengewnc.model.request.TimestampAverageRequest;
import com.acatalan.challengewnc.model.response.TimestampAverageResponse;

import javassist.NotFoundException;

public interface LastPriceService {
	
	public void loadPrices();

	public List<Price> findAll() throws NotFoundException;

	public LastPrice findById(Long id) throws NotFoundException;

	public LastPrice findByTimestamp(String timestamp) throws NotFoundException;

	public TimestampAverageResponse getTimestampAverage(TimestampAverageRequest request) throws NotFoundException;

}
