package com.sistema.app.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.app.models.dao.FamilyDAO;
import com.sistema.app.models.documents.Family;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FamilyServiceImpl implements FamilyService{

	@Autowired
	private FamilyDAO fdao;
	
	@Override
	public Flux<Family> findAllFamily() {
		return fdao.findAll();
	}

	@Override
	public Mono<Family> findByIdFamily(String id) {
		return fdao.findById(id);
	}

	@Override
	public Mono<Family> saveFamily(Family family) {
		return fdao.save(family);
	}

	@Override
	public Mono<Void> deleteFamily(Family family) {
		return fdao.delete(family);
	}

	@Override
	public Flux<Family> findAllFamilyByIdStudent(String idStudent) {
		return fdao.findAll(idStudent);
	}

	

}
