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
import com.grupo5turmajava81.aplicativocarona.model.Caronas;
import com.grupo5turmajava81.aplicativocarona.repository.CaronasRepository;
import com.grupo5turmajava81.aplicativocarona.repository.MotoristaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/caronas")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class CaronasController {

	@Autowired
	private CaronasRepository caronasRepository;

	@Autowired
	private MotoristaRepository motoristaRepository;

	@GetMapping
	public ResponseEntity<List<Caronas>> getAll() {
		return ResponseEntity.ok(caronasRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Caronas> getById(@PathVariable Long id) {
		return caronasRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/destino/{destino}")
	public ResponseEntity<List<Caronas>> getByDestino(@PathVariable String destino) {
		return ResponseEntity.ok(caronasRepository.findAllByDestinoContainingIgnoreCase(destino));
	}

	@PostMapping
	public ResponseEntity<Caronas> post(@Valid @RequestBody Caronas caronas) {
		if (motoristaRepository.existsById(caronas.getMotorista().getId()))
			return ResponseEntity.status(HttpStatus.CREATED).body(caronasRepository.save(caronas));

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Carona não existe!", null);
	}

	@PutMapping
	public ResponseEntity<Caronas> put(@Valid @RequestBody Caronas caronas) {
		if (caronasRepository.existsById(caronas.getId())) {

			if (motoristaRepository.existsById(caronas.getMotorista().getId()))
				return ResponseEntity.status(HttpStatus.OK).body(caronasRepository.save(caronas));

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Carona não existe!", null);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {

		Optional<Caronas> caronas = caronasRepository.findById(id);

		if (caronas.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		caronasRepository.deleteById(id);
	}

}
