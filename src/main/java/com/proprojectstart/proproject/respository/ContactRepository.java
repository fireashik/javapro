package com.proprojectstart.proproject.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proprojectstart.proproject.model.Contact;
@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
