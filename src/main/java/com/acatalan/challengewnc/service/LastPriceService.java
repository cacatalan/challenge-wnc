package com.acatalan.challengewnc.service;

import java.util.List;

import com.acatalan.challengewnc.model.dto.LastPrice;
import com.acatalan.challengewnc.model.entity.Price;
import com.acatalan.challengewnc.model.request.LastPriceByTimestamp;

import javassist.NotFoundException;

public interface LastPriceService {
	
	public void loadPrices();

	public List<Price> findAll();

	public LastPrice findById(Long id) throws NotFoundException;

	public LastPrice findByTimestamp(LastPriceByTimestamp request) throws NotFoundException;

}
