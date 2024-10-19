package com.proprojectstart.proproject.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proprojectstart.proproject.model.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
			
}
