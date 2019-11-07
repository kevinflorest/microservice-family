package com.sistema.app;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.sistema.app.documents.Family;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiFamilyApplicationTests {

	@Autowired
	private WebTestClient client; 
	

	@Test
	public void contextLoads() {
	}
	
//	@Test
//	public void listFamilies() {
//		client.get().uri("/api/family/")
//		.accept(MediaType.APPLICATION_JSON_UTF8)
//		.exchange()
//		.expectStatus().isOk()
//		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
//		.expectBodyList(Family.class).consumeWith(response -> {
//			List<Family> families = response.getResponseBody();
//			
//			families.forEach(p -> {
//				System.out.println(p.getId());
//			});
//			
//			Assertions.assertThat(families.size()>0).isTrue();
//		});;
//	}
	
}
