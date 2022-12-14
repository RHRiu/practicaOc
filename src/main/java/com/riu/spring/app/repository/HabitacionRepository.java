package com.riu.spring.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riu.spring.app.model.Habitacion;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
	List<Habitacion> findAll();
	List<Habitacion> findByNameContaining(String name);
}
