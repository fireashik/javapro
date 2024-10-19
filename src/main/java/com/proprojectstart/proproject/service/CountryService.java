package com.proprojectstart.proproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proprojectstart.proproject.model.Country;
import com.proprojectstart.proproject.respository.CountryRepository;

@Service
public class CountryService {
@Autowired
	private CountryRepository countryRepository;
			public List <Country> countryList() {
				return countryRepository.findAll();
			}
}
