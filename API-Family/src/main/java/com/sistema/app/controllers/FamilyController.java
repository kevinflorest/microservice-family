package com.sistema.app.controllers;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.sistema.app.documents.Family;
import com.sistema.app.exception.RequestException;
import com.sistema.app.services.FamilyService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/family/")
public class FamilyController {
	
	@Autowired 
	private FamilyService service;
	
	  @GetMapping 
	  public Flux<Family> listFamily()
	  { 
		  Flux<Family> family = service.findAllFamily();
		  return family;
	  }

	@GetMapping("idStudent/{idStudent}")
	public Flux<Family> listFamilyByIdStudent(@PathVariable String idStudent){
		 Flux<Family> family = service.findAllFamilyByIdStudent(idStudent);
		  return family;
	}
	
	@GetMapping("/{id}")
	public Mono<Family> familyById(@PathVariable String id){
		Mono<Family> family = service.findByIdFamily(id);
		return family;
	}
	
	@GetMapping("/document/{numberDocument}")
	public Mono<Family> familyByDocument(@PathVariable String numberDocument){
		Mono<Family> family = service.findByDocumentFamily(numberDocument);
		return family;
	}
		
	@GetMapping("count/{idStudent}")
	public Flux<Family> qtyParents(@PathVariable String idStudent) {
		 Flux<Family> family = service.findParents(idStudent);
		 return family;
	}
	
	
	@PostMapping
	public Flux<Family> saveFamily(@Valid @RequestBody List<Family> family){
			return service.saveFamily(family);
	}
	 
	/*
	 * @PutMapping("/{id}") public Mono<ResponseEntity<Family>>
	 * updateFamily(@RequestBody Family family, @PathVariable String id) { return
	 * service.findByIdFamily(id) .flatMap(f -> {
	 * f.setFirstName(family.getFirstName());
	 * f.setSecondName(family.getSecondName());
	 * f.setPaternalSurname(family.getPaternalSurname());
	 * f.setMaternalSurname(family.getMaternalSurname());
	 * f.setGenderFamily(family.getGenderFamily());
	 * f.setBirthDate(family.getBirthDate());
	 * f.setTypeDocument(family.getTypeDocument());
	 * f.setNumberDocument(family.getNumberDocument()); return
	 * service.saveFamily(f); }).map(f ->
	 * ResponseEntity.created(URI.create("/api/family/".concat(f.getId())))
	 * .contentType(MediaType.APPLICATION_JSON_UTF8) .body(f))
	 * .defaultIfEmpty(ResponseEntity.notFound().build()); }
	 */
	
	/*
	 * @DeleteMapping("/{id}") public Mono<ResponseEntity<Void>>
	 * deleteFamily(@PathVariable String id) { return
	 * service.findByIdFamily(id).flatMap(f -> { return
	 * service.deleteFamily(f).then(Mono.just(new
	 * ResponseEntity<Void>(HttpStatus.NO_CONTENT))); }).defaultIfEmpty(new
	 * ResponseEntity<Void>(HttpStatus.NO_CONTENT)); }
	 */
	
	@DeleteMapping("/{id}")
	public Mono<Void> deleteFamily(@PathVariable String id)
	{
		return service.deleteFamily(id);
	}
}
