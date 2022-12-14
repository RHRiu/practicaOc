package com.riu.spring.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riu.spring.app.model.Habitacion;
import com.riu.spring.app.repository.HabitacionRepository;


@RestController
@RequestMapping("/api")
public class HabitacionController {

	@Autowired
	HabitacionRepository habitacionRepository;

	@GetMapping("/habitaciones")
	public ResponseEntity<List<Habitacion>> getAllHabitaciones(@RequestParam(required = false) String title) {
		try {
			List<Habitacion> habitaciones = new ArrayList<Habitacion>();

			if (title == null)
				habitacionRepository.findAll().forEach(habitaciones::add);
			else
				habitacionRepository.findByNameContaining(title).forEach(habitaciones::add);

			if (habitaciones.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(habitaciones, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/habitaciones/{id}")
	public ResponseEntity<Habitacion> getHabitacionById(@PathVariable("id") long id) {
		Optional<Habitacion> habitacionData = habitacionRepository.findById(id);

		if (habitacionData.isPresent()) {
			return new ResponseEntity<>(habitacionData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/habitaciones")
	public ResponseEntity<Habitacion> createHabitacion(@RequestBody Habitacion habitacion) {
		try {
			Habitacion v_habitacion = habitacionRepository
					.save(new Habitacion(habitacion.getName(), habitacion.getDescription()));
			return new ResponseEntity<>(v_habitacion, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/habitaciones/{id}")
	public ResponseEntity<Habitacion> updateHabitacion(@PathVariable("id") long id, @RequestBody Habitacion habitacion) {
		Optional<Habitacion> habitacionData = habitacionRepository.findById(id);

		if (habitacionData.isPresent()) {
			Habitacion v_habitacion = habitacionData.get();
			v_habitacion.setName(habitacion.getName());
			v_habitacion.setDescription(habitacion.getDescription());
			return new ResponseEntity<>(habitacionRepository.save(v_habitacion), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/habitaciones/{id}")
	public ResponseEntity<HttpStatus> deleteHabitacion(@PathVariable("id") long id) {
		try {
			habitacionRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/habitaciones")
	public ResponseEntity<HttpStatus> deleteAllHotels() {
		try {
			habitacionRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
