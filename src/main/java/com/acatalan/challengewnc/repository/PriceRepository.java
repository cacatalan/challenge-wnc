package com.acatalan.challengewnc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acatalan.challengewnc.model.entity.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

}
