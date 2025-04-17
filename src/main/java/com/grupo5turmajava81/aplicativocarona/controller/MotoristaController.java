package com.grupo5turmajava81.aplicativocarona.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.grupo5turmajava81.aplicativocarona.model.Motorista;
import com.grupo5turmajava81.aplicativocarona.repository.MotoristaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/motorista")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class MotoristaController {

	@Autowired
	private MotoristaRepository motoristaRepository;


	@GetMapping
	public ResponseEntity<List<Motorista>> getAll() {
		return ResponseEntity.ok(motoristaRepository.findAll());

	}

	@GetMapping("/{id}")
	public ResponseEntity<Motorista> getById(@PathVariable Long id) {
		return motoristaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Motorista>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(motoristaRepository.findAllByNomeContainingIgnoreCase(nome));
	}

	@PostMapping
	public ResponseEntity<Motorista> post(@Valid @RequestBody Motorista motorista) {
		return ResponseEntity.status(HttpStatus.CREATED).body(motoristaRepository.save(motorista));
	}

	@PutMapping
	public ResponseEntity<Motorista> put(@Valid @RequestBody Motorista motorista) {
		return motoristaRepository.findById(motorista.getId())
				.map(reposta -> ResponseEntity.status(HttpStatus.CREATED).body(motoristaRepository.save(motorista)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Motorista> motorista = motoristaRepository.findById(id);

		if (motorista.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		motoristaRepository.deleteById(id);
	}
	
	
}
