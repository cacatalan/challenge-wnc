package com.acatalan.challengewnc.service.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acatalan.challengewnc.client.LastPriceClient;
import com.acatalan.challengewnc.model.dto.LastPrice;
import com.acatalan.challengewnc.model.entity.Price;
import com.acatalan.challengewnc.model.request.TimestampAverageRequest;
import com.acatalan.challengewnc.model.response.TimestampAverageResponse;
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

	public List<Price> findAll() throws NotFoundException {
		List<Price> list = repository.findAll(); 
		if (list.stream().count()>0) {
			return list;
		} else {
			throw new NotFoundException("Price list not found");	
		}
	}

	public LastPrice findById(Long id) throws NotFoundException {
		Optional<Price> priceOpt = repository.findById(id);
		if (priceOpt.isPresent()) {
			return mapper.convertValue(priceOpt.get(), LastPrice.class);
		} else {
			throw new NotFoundException("Cannot find the price with id: "+id);
		}
	}

	public LastPrice findByTimestamp(String timestamp) throws NotFoundException {
		Stream<Price> listPrices = this.findAll().stream();
		Price price = listPrices
						.filter(p -> timestamp.equals(p.getTimestamp()))
						.findAny()
						.orElse(null);
		
		if (price == null) {
			throw new NotFoundException("Cannot find the price with timestamp: "+timestamp);
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

	public TimestampAverageResponse getTimestampAverage(TimestampAverageRequest request) throws NotFoundException {

		List<Price> listPrices = this.findAll();
		
		String maxValue = this.getMaxPriceOfList(listPrices);
		String timestampAverage = this.getTimestampAverageOfList(listPrices, request.getTime1(), request.getTime2());
		String percentageDifference = this.getPercentageDifference(maxValue, timestampAverage).toString();

		TimestampAverageResponse response = new TimestampAverageResponse(timestampAverage, percentageDifference, maxValue);
		
		return response;
	}

	public Double getPercentageDifference(String maxValue, String timestampAverage) {
		Double maxValueD = Double.parseDouble(maxValue);
		Double timestampAverageD = Double.parseDouble(timestampAverage);
		Double percentageDifference = ((maxValueD-timestampAverageD)/maxValueD)*100;
		return percentageDifference;
	}

	public String getTimestampAverageOfList(List<Price> listPrices, String time1, String time2) throws NotFoundException {
		
        Predicate<Price> isPriceEqualsTime1 = p -> time1.equals(p.getTimestamp());
        Predicate<Price> isPriceEqualsTime2 = p -> time2.equals(p.getTimestamp());
		
		Double average = listPrices.stream()
				.filter(isPriceEqualsTime1.or(isPriceEqualsTime2))
				.mapToDouble(Price::getLprice)
				.average()
				.getAsDouble();
		
		return average.toString();
		
	}

	public String getMaxPriceOfList(List<Price> listPrices) {
		
		Optional<Price> maxPrice = listPrices.stream()
				.max(Comparator.comparing(Price::getLprice));

		Double maxValue = null;
		if (maxPrice.isPresent()) {
			maxValue = maxPrice.get().getLprice();
		}
		
		return maxValue.toString();
		
	}

}
