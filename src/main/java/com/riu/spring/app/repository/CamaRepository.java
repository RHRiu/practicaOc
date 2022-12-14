package com.riu.spring.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riu.spring.app.model.Cama;

@Repository
public interface CamaRepository extends JpaRepository<Cama, Long> {
	List<Cama> findAll();
	List<Cama> findByNameContaining(String name);
}
