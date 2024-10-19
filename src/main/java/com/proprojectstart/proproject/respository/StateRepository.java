package com.proprojectstart.proproject.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proprojectstart.proproject.model.Country;
import com.proprojectstart.proproject.model.State;

public interface StateRepository extends JpaRepository<State, Integer> {
			List<State> findByCountry(Country countryid);
}
