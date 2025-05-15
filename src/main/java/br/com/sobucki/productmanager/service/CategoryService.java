package br.com.sobucki.productmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sobucki.productmanager.dto.CategoryDTO;
import br.com.sobucki.productmanager.repository.category.CategoryRepository;

@Service
public class CategoryService {
  private final CategoryRepository repository;

  @Autowired
  public CategoryService(CategoryRepository repository) {
    this.repository = repository;
  }

  public List<CategoryDTO> getAllCategories() {
    return repository.findAll().stream()
        .map(category -> new CategoryDTO(category.getId(), category.getName(), category.getDescription()))
        .toList();
  }

}
