package com.acatalan.challengewnc.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity(name = "PRICES")
@Data
public class Price {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String lprice;
	
	private String curr1;
	
	private String curr2;
	
	private String timestamp;

}
