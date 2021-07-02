package com.nayan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nayan.entity.Contacts;

public interface ContactRepo extends JpaRepository<Contacts, Integer> {

}
