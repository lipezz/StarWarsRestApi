package com.swRestApi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.swRestApi.model.Planeta;

@Repository
public interface PlanetaRepository extends MongoRepository<Planeta, Long> {	
		
	Optional<Planeta> findByNome(String nome);	
	Optional<Planeta> findBySwApiId(String swApiId);
	Optional<Planeta> deleteByNome(String nome);	
}