package com.pietrobraga.backend.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pietrobraga.backend.dto.ProductDTO;
import com.pietrobraga.backend.entities.Product;
import com.pietrobraga.backend.repositories.ProductRepository;
import com.pietrobraga.backend.services.exceptions.DatabaseException;
import com.pietrobraga.backend.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		Page<Product> products = repository.findAll(pageRequest);
		return products.map(x -> new ProductDTO(x, x.getCategories()));
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id){
		Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		ProductDTO productDto = new ProductDTO(product, product.getCategories());
		return productDto;
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		//entity.setName(dto.getName());
		repository.save(entity);
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getOne(id);
			//entity.setName(dto.getName());
			repository.save(entity);
			return new ProductDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found "+id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	
	}
	
}