package com.pietrobraga.backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pietrobraga.backend.dto.CategoryDTO;
import com.pietrobraga.backend.entities.Category;
import com.pietrobraga.backend.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> categories = repository.findAll();
		List<CategoryDTO> categoriesDto = categories.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		return categoriesDto;
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id){
		Category category = repository.findById(id).get();
		CategoryDTO categoryDto = new CategoryDTO(category);
		return categoryDto;
	}
	
	
}
