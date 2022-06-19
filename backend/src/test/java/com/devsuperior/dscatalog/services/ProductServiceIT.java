package com.devsuperior.dscatalog.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@SpringBootTest
public class ProductServiceIT {
	
	@Autowired
	private ProductService service;
	
	@Autowired
	private ProductRepository repository;
	
	private long existingId;
	private long nonExixtingId;
	private long dependentId;
	private long countTotalProducts;
	
	
	@BeforeEach
	void setUp() throws Exception {
		
		existingId = 1L;
		nonExixtingId = 1000L;
		countTotalProducts = 25L;
		
	}
	
	@Test
	public void deleteShouldDeleteResourceWhenIdExists() {
		service.delete(existingId);
		
		Assertions.assertEquals(countTotalProducts-1, repository.count());
		
	}

	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class,  () -> {
			service.delete(nonExixtingId);
		});
		
		
	}
	
	
	
}
