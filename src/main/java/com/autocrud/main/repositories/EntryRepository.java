package com.autocrud.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autocrud.main.entities.Entry;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

}
