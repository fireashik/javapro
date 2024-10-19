package com.proprojectstart.proproject.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.proprojectstart.proproject.model.ProjectModel;
@Repository
public interface ProjectRepository extends JpaRepository<ProjectModel, Integer> {


			public ProjectModel findByVerificationcode(String code);
			public ProjectModel findByOtp(String otp);
			public ProjectModel findByEmail(String email);
	
			
			
			
}
