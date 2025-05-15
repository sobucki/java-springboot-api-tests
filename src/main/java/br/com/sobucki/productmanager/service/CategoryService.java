package br.com.sobucki.productmanager.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sobucki.productmanager.dto.CategoryDTO;
import br.com.sobucki.productmanager.model.Category;
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
        .map(category -> createCategoryDTO(category))
        .toList();
  }

  public CategoryDTO createCategory(CategoryDTO categoryDTO) {
    Category category = createCategoryFromDTO(categoryDTO);
    Category savedCategory = repository.save(category);
    return createCategoryDTO(savedCategory);
  }

  public CategoryDTO updateCategory(UUID id, Category product) {
    Category existingCategory = repository.findById(id);
    if (existingCategory == null) {
      return null;
    }

    existingCategory.setName(product.getName());
    existingCategory.setDescription(product.getDescription());

    repository.save(existingCategory);

    return createCategoryDTO(existingCategory);
  }

  public void deleteCategory(UUID id) {
    repository.delete(id);
  }

  public CategoryDTO getCategoryById(UUID id) {
    Category category = repository.findById(id);
    if (category == null) {
      return null;
    }
    return createCategoryDTO(category);
  }

  public Category createCategoryFromDTO(CategoryDTO categoryDTO) {
    Category category = new Category();
    category.setName(categoryDTO.getName());
    category.setDescription(categoryDTO.getDescription());
    return category;
  }

  public CategoryDTO createCategoryDTO(Category category) {
    return new CategoryDTO(category.getId(), category.getName(), category.getDescription());
  }

}
