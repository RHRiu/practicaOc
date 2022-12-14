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

import com.riu.spring.app.model.Cama;
import com.riu.spring.app.repository.CamaRepository;


@RestController
@RequestMapping("/api")
public class CamaController {

	@Autowired
	CamaRepository camaRepository;

	@GetMapping("/camas")
	public ResponseEntity<List<Cama>> getAllCamas(@RequestParam(required = false) String title) {
		try {
			List<Cama> camas = new ArrayList<Cama>();

			if (title == null)
				camaRepository.findAll().forEach(camas::add);
			else
				camaRepository.findByNameContaining(title).forEach(camas::add);

			if (camas.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(camas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/camas/{id}")
	public ResponseEntity<Cama> getCamaById(@PathVariable("id") long id) {
		Optional<Cama> camaData = camaRepository.findById(id);

		if (camaData.isPresent()) {
			return new ResponseEntity<>(camaData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/camas")
	public ResponseEntity<Cama> createCama(@RequestBody Cama cama) {
		try {
			Cama v_cama = camaRepository
					.save(new Cama(cama.getName(), cama.getDescription()));
			return new ResponseEntity<>(v_cama, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/camas/{id}")
	public ResponseEntity<Cama> updateCama(@PathVariable("id") long id, @RequestBody Cama cama) {
		Optional<Cama> camaData = camaRepository.findById(id);

		if (camaData.isPresent()) {
			Cama v_cama = camaData.get();
			v_cama.setName(cama.getName());
			v_cama.setDescription(cama.getDescription());
			return new ResponseEntity<>(camaRepository.save(v_cama), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/camas/{id}")
	public ResponseEntity<HttpStatus> deleteCama(@PathVariable("id") long id) {
		try {
			camaRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/camas")
	public ResponseEntity<HttpStatus> deleteAllHotels() {
		try {
			camaRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
