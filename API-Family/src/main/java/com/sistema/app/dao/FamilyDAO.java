package com.sistema.app.dao;


import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.sistema.app.documents.Family;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FamilyDAO extends ReactiveMongoRepository<Family, String> {

	@Query("{ 'idStudent' : ?0}")
	Flux<Family> findAll(String idStudent);
	
	@Query("{ 'numberDocument' : ?0}")
	Mono<Family> findFamilyByDocument(String numberDocument);
	
	@Query(value = "{'relationship' : {'$in':['Padre','Madre']}, 'idStudent' : ?0}")
	Flux<Family> ParentsByIdStudent(String idStudent);
}
