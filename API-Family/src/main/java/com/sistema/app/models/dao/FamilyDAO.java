package com.sistema.app.models.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.sistema.app.models.documents.Family;

import reactor.core.publisher.Flux;

public interface FamilyDAO extends ReactiveMongoRepository<Family, String> {

	@Query("{ 'idStudent' : ?0}")
	Flux<Family> findAll(String idStudent);
}
