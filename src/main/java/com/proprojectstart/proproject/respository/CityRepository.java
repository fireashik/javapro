package com.proprojectstart.proproject.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proprojectstart.proproject.model.City;
import com.proprojectstart.proproject.model.State;

public interface CityRepository extends JpaRepository<City, Integer> {
			List<City> findByState(State stateid);
}
