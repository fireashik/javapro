package com.proprojectstart.proproject.respository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.proprojectstart.proproject.model.FormModel;

public interface FormRepository extends JpaRepository<FormModel, Integer> {

}
