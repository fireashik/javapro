package com.proprojectstart.proproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proprojectstart.proproject.model.Country;
import com.proprojectstart.proproject.model.State;
import com.proprojectstart.proproject.respository.StateRepository;

@Service
public class StateService {
@Autowired
		private StateRepository StateRepository;

			public List <State> getstate(){
				return StateRepository.findAll();
			}
			public List<State>geStateBy(Country countryid){
				return StateRepository.findByCountry(countryid);
			}
}
