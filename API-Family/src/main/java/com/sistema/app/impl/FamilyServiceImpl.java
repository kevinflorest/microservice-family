package com.sistema.app.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.app.dao.FamilyDAO;
import com.sistema.app.documents.Family;
import com.sistema.app.exception.RequestException;
import com.sistema.app.services.FamilyService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FamilyServiceImpl implements FamilyService {

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
	public Flux<Family> saveFamily(List<Family> family) {
		Flux<Family> fMono = Flux.fromIterable(family);
		
		return fMono
				.filter(ff->{
			        if (ff.getRelationship().equalsIgnoreCase("Padre") || ff.getRelationship().equalsIgnoreCase("Madre") ||
			        		ff.getRelationship().equalsIgnoreCase("Conyuge") || ff.getRelationship().equalsIgnoreCase("Hermano")) {
			            return true;
			        }
			        return false;
			        })
				.flatMap(f->{
					Mono<Long> valor = fdao.ParentsByIdStudent(f.getIdStudent()).count();
					return valor.flatMap(f2->{
						if(f2==2) {
							if (!f.getRelationship().equalsIgnoreCase("Padre") && !f.getRelationship().equalsIgnoreCase("Madre") ){
								Family f1 = new Family();
								f1.setIdStudent(f.getIdStudent());
								f1.setBirthDate(f.getBirthDate());
								f1.setTypeDocument(f.getTypeDocument());
								f1.setGenderFamily(f.getGenderFamily());
								f1.setFirstName(f.getFirstName());
								f1.setSecondName(f.getSecondName());
								f1.setRelationship(f.getRelationship());
								f1.setNumberDocument(f.getNumberDocument());
								return fdao.save(f1);
								
					        }else {
					        	throw new RequestException("El estudiante ya cuenta con padres");
					        }
						}else{
							Family f1 = new Family();
							f1.setIdStudent(f.getIdStudent());
							f1.setBirthDate(f.getBirthDate());
							f1.setTypeDocument(f.getTypeDocument());
							f1.setGenderFamily(f.getGenderFamily());
							f1.setFirstName(f.getFirstName());
							f1.setSecondName(f.getSecondName());
							f1.setRelationship(f.getRelationship());
							f1.setNumberDocument(f.getNumberDocument());
							return fdao.save(f1);
						}
					});
			
				});
		
	}


	@Override
	public Mono<Void> deleteFamily(String id) {
		return fdao.deleteById(id);
	}

	@Override
	public Flux<Family> findAllFamilyByIdStudent(String idStudent) {

		return fdao.findAll(idStudent);
	}

	@Override
	public Flux<Family> findParents(String idStudent) {
		return fdao.ParentsByIdStudent(idStudent);
	}

	@Override
	public Mono<Family> findByDocumentFamily(String numberDocument) {
		return fdao.findFamilyByDocument(numberDocument);
	}



}
