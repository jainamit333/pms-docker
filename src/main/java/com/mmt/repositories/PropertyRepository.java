package com.mmt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmt.entity.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
	public Property findOneById(Integer id);
}
