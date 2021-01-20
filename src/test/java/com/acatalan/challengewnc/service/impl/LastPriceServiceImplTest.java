package com.acatalan.challengewnc.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.acatalan.challengewnc.model.entity.Price;
import com.acatalan.challengewnc.model.request.TimestampAverageRequest;

import javassist.NotFoundException;

class LastPriceServiceImplTest {
	
	@Autowired
	LastPriceServiceImpl service = new LastPriceServiceImpl();

	@Test
	void getMaxPriceOfList() throws NotFoundException {
		String response = service.getMaxPriceOfList(getPricesFromDataSource());
		assertTrue(response.equals("11.222"));
	}

	@Test
	void getTimestampAverageOfList() throws NotFoundException {
		TimestampAverageRequest request = new TimestampAverageRequest();
		request.setTime1("time1");
		request.setTime2("time3");
		String response = service.getTimestampAverageOfList(getPricesFromDataSource(), "time1", "time3");
		assertTrue(response.equals("10.212"));
	}

	@Test
	void getPercentageDifference() throws NotFoundException {
		Double response = service.getPercentageDifference("150", "120");
		assertTrue(response==20);
	}
	
	private static List<Price> getPricesFromDataSource() {
        List<Price> priceList = new ArrayList<Price>();
        
        Long id = 0L;
        
        Price price1 = new Price();
        price1.setCurr1("BTC");
        price1.setCurr2("USD");
        price1.setId(id++);
        price1.setLprice("10.002");
        price1.setTimestamp("time1");
        
        Price price2 = new Price();
        price2.setCurr1("BTC");
        price2.setCurr2("USD");
        price2.setId(id++);
		price2.setLprice("10.522");
		price2.setTimestamp("time2");
        
        Price price3 = new Price();
        price3.setCurr1("BTC");
        price3.setCurr2("USD");
		price3.setId(id++);
		price3.setLprice("10.422");
		price3.setTimestamp("time3");
        
        Price price4 = new Price();
        price4.setCurr1("BTC");
        price4.setCurr2("USD");
		price4.setId(id++);
		price4.setLprice("11.222");
		price4.setTimestamp("time4");
        
        Price price5 = new Price();
        price5.setCurr1("BTC");
        price5.setCurr2("USD");
		price5.setId(id++);
		price5.setLprice("10.802");
		price5.setTimestamp("time5");
        
        Price price6 = new Price();
        price6.setCurr1("BTC");
        price6.setCurr2("USD");
		price6.setId(id++);
		price6.setLprice("10.472");
		price6.setTimestamp("time6");
		
		priceList.add(price1);
		priceList.add(price2);
		priceList.add(price3);
		priceList.add(price4);
		priceList.add(price5);
		priceList.add(price6);
         
        return priceList;
    }

}
