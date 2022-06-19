package com.devsuperior.dscatalog.services;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	private long existingId;
	private long nonExixtingId;
	private long dependentId;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExixtingId = 1000L;
		dependentId = 4L;
		doNothing().when(repository).deleteById(existingId);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExixtingId);
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		Assertions.assertThrows(DatabaseException.class,  () -> {
			service.delete(dependentId);
		});
		
		verify(repository, times(1)).deleteById(dependentId);
	}
	
	
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class,  () -> {
			service.delete(nonExixtingId);
		});
		
		verify(repository, times(1)).deleteById(nonExixtingId);
	}
	
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		
		verify(repository, times(1)).deleteById(existingId);
	}
	
}
