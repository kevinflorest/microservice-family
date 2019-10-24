package com.sistema.app.controllers;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import com.sistema.app.models.documents.Family;
import com.sistema.app.models.services.FamilyService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/family/")
public class FamilyController {
	
	@Autowired 
	private FamilyService service;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Family>>> listFamily(){
		return Mono.just(
				ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAllFamily()));
	}
	
	@GetMapping("idStudent/{idStudent}")
	public Mono<ResponseEntity<Flux<Family>>> listFamilyByIdStudent(@PathVariable String idStudent){
		return Mono.just(
				ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAllFamilyByIdStudent(idStudent)));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Family>> FamilyById(@PathVariable String id){
		return service.findByIdFamily(id).map(f -> 
		ResponseEntity
		.ok()
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.body(f))
	    .defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> saveFamily(@Valid @RequestBody Mono<Family> monoFamily){
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		return monoFamily.flatMap(family -> {
			return service.saveFamily(family).map(f-> {
				response.put("family", f);
				response.put("mensaje", "Familiar registrado con éxito");
				response.put("timestamp", new Date());
				return ResponseEntity
					.created(URI.create("/api/family/".concat(f.getId())))
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(response);
				});
			
		}).onErrorResume(t -> {
			return Mono.just(t).cast(WebExchangeBindException.class)
					.flatMap(e -> Mono.just(e.getFieldErrors()))
					.flatMapMany(Flux::fromIterable)
					.map(fieldError -> "El campo "+fieldError.getField() + " " + fieldError.getDefaultMessage())
					.collectList()
					.flatMap(list -> {
						response.put("errors", list);
						response.put("timestamp", new Date());
						response.put("status", HttpStatus.BAD_REQUEST.value());
						return Mono.just(ResponseEntity.badRequest().body(response));
					});
							
		});
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Family>> updateFamily(@RequestBody Family family, @PathVariable String id)
	{
		return service.findByIdFamily(id)
				.flatMap(f -> {
					f.setFirstName(family.getFirstName());
					f.setSecondName(family.getSecondName());
					f.setPaternalSurname(family.getPaternalSurname());
					f.setMaternalSurname(family.getMaternalSurname());
					f.setGenderFamily(family.getGenderFamily());
					f.setBirthDate(family.getBirthDate());
					f.setTypeDocument(family.getTypeDocument());
					f.setNumberDocument(family.getNumberDocument());
					return service.saveFamily(f);
				}).map(f -> ResponseEntity.created(URI.create("/api/family/".concat(f.getId())))
				  .contentType(MediaType.APPLICATION_JSON_UTF8)
				  .body(f))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}	
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteFamily(@PathVariable String id)
	{
		return service.findByIdFamily(id).flatMap(f -> {
			return service.deleteFamily(f).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));		
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
	}
}
