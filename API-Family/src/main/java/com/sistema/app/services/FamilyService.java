package com.sistema.app.services;

import java.util.List;

import com.sistema.app.documents.Family;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FamilyService {

	Flux<Family> findAllFamily();
	
	Mono<Family> findByIdFamily(String id);
	
	Flux<Family> saveFamily(List<Family> family);
		
	Mono<Void> deleteFamily(String id);
	
	Flux<Family> findAllFamilyByIdStudent(String idStudent);
	
	Flux<Family> findParents(String idStudent);
	
	Mono<Family> findByDocumentFamily(String numberDocument);
}
