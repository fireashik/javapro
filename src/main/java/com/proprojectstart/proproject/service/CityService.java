package com.proprojectstart.proproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proprojectstart.proproject.model.City;
import com.proprojectstart.proproject.model.State;
import com.proprojectstart.proproject.respository.CityRepository;

@Service
public class CityService {
		@Autowired
				private CityRepository cityRepository;
		public List <City> getcity(){
			return cityRepository.findAll();
		}
		public List <City> getCityBy(State stateid){
			return cityRepository.findByState(stateid);
		}
}
