package com.acatalan.challengewnc.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.acatalan.challengewnc.model.entity.Price;

@DataJpaTest
class PriceRepositoryTest {

    @Autowired
    private PriceRepository repository;

	@Test
	void whenFindAll_ThenReturnPriceList() {
		Price price = this.getPriceDefault(1L);
		repository.save(price);			
		List<Price> result = repository.findAll();
			
		assertNotNull(result);
		assertTrue(result.size()>0);
	}

	@Test
	void whenSave_ThenReturnPriceByIdAndSaveSuccessfully() {
		Price price = this.getPriceDefault(2L);
		repository.save(price);
		Optional<Price> result = repository.findById(2L);
		
		assertTrue(result.isPresent());
		assertTrue(result.get().getId()==2L);
	}

	@Test
	void whenFindByID_ThenReturnPriceByIdSuccessfully() {
		Price price = this.getPriceDefault(3L);
		repository.save(price);
		Optional<Price> result = repository.findById(3L);
		
		assertTrue(result.isPresent());
		assertTrue(result.get().getId()==3L);
	}

	/**
	 * Get price default and id parameter
	 * Obtiene un objeto precio con los datos default y id de parametro.
	 * @param id -- id of Price
	 * @return Price data default with id parameter.
	 */
	private Price getPriceDefault(Long id) {
		Price price = new Price();
			price.setId(id);
			price.setLprice("10.10");
			price.setCurr1("BTC");
			price.setCurr2("USD");
			price.setTimestamp("132456789");
		return price;
	}

}
