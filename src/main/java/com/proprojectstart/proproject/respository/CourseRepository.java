package com.proprojectstart.proproject.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proprojectstart.proproject.model.Courses;
@Repository
public interface CourseRepository extends JpaRepository<Courses, Integer> {
	
}
