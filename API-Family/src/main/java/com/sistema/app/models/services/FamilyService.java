package com.sistema.app.models.services;

import com.sistema.app.models.documents.Family;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FamilyService {

	Flux<Family> findAllFamily();
	
	Mono<Family> findByIdFamily(String id);
	
	Mono<Family> saveFamily(Family family);
		
	Mono<Void> deleteFamily(Family family);
	
	Flux<Family> findAllFamilyByIdStudent(String idStudent);
}
