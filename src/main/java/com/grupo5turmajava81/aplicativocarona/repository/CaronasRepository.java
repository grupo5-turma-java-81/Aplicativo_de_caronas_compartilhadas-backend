package com.grupo5turmajava81.aplicativocarona.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.grupo5turmajava81.aplicativocarona.model.Caronas;

public interface CaronasRepository extends JpaRepository<Caronas, Long> {
	
	public List<Caronas>findAllByDestinoContainingIgnoreCase(@Param("destino")String destino);
}
