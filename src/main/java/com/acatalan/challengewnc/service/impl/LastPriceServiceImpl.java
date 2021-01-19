package com.acatalan.challengewnc.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acatalan.challengewnc.client.LastPriceClient;
import com.acatalan.challengewnc.model.dto.LastPrice;
import com.acatalan.challengewnc.model.entity.Price;
import com.acatalan.challengewnc.model.request.LastPriceByTimestamp;
import com.acatalan.challengewnc.repository.PriceRepository;
import com.acatalan.challengewnc.service.LastPriceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javassist.NotFoundException;
import reactor.core.publisher.Flux;

@Service
public class LastPriceServiceImpl implements LastPriceService {

	@Autowired
	private LastPriceClient client;
	
	@Autowired
	private PriceRepository repository;
	
	@Autowired
	private ObjectMapper mapper;

	public List<Price> findAll() {
		return repository.findAll();
	}

	public LastPrice findById(Long id) throws NotFoundException {
		Optional<Price> priceOpt = repository.findById(id);
		if (priceOpt.isPresent()) {
			return mapper.convertValue(priceOpt.get(), LastPrice.class);
		} else {
			throw new NotFoundException("Cannot find the price with id: "+id);
		}
	}

	public LastPrice findByTimestamp(LastPriceByTimestamp request) throws NotFoundException {
		Stream<Price> listPrices = this.findAll().stream();
		Price price = listPrices
						.filter(p -> request.getTimestamp().equals(p.getTimestamp()))
						.findAny()
						.orElse(null);
		
		if (price == null) {
			throw new NotFoundException("Cannot find the price with timestamp: "+request.getTimestamp());
		}
		
		return mapper.convertValue(price, LastPrice.class);
	}

	public void loadPrices() {
		
		List<Price> listPrices = client.loadPrices().block();
		
		listPrices.stream().forEach(item -> {
			repository.save(item);
		});

	}

	public void loadPrices2() {

		Flux<String> responseClient = client.loadPrices2();
		List<String> listPrices = responseClient.collectList().block();
		
		listPrices.stream().forEach(item -> {
			try {
				Price price = mapper.readValue(item.toString(), Price.class);
				price.setTimestamp(LocalDateTime.now().toString());
				repository.save(price);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});

	}

}
