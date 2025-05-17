package br.com.sobucki.productmanager.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.UUID;

import br.com.sobucki.productmanager.dto.CategoryDTO;
import br.com.sobucki.productmanager.model.Category;
import br.com.sobucki.productmanager.repository.category.CategoryRepository;

public class CategoryServiceTest {

  private CategoryRepository repository;
  private CategoryService categoryService;

  @BeforeEach
  public void setUp() {
    repository = mock(CategoryRepository.class);
    categoryService = new CategoryService(repository);
  }

  @Test
  @DisplayName("Should return all categories")
  public void shouldReturnAllCategories() {
    when(repository.findAll()).thenReturn(List.of());
    List<CategoryDTO> categories = categoryService.getAllCategories();

    assertEquals(0, categories.size());
    verify(repository).findAll();
  }

  @Test
  @DisplayName("Should create a category")
  public void shouldCreateCategory() {
    CategoryDTO categoryDTO = new CategoryDTO(null, "Vestuário", "Roupa e Acessórios");
    UUID id = UUID.randomUUID();
    when(repository.save(any())).thenReturn(new Category(id, "Vestuário", "Roupa e Acessórios"));

    CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);

    assertEquals("Vestuário", createdCategory.getName());
    assertEquals("Roupa e Acessórios", createdCategory.getDescription());
    verify(repository).save(any());
  }

  @Test
  @DisplayName("Should update a category")
  public void shouldUpdateCategory() {
    UUID id = UUID.randomUUID();

    Category foundCategory = new Category(id, "Vestuário", "Roupa e Acessórios");
    Category updated = new Category(id, "Vestuário", "Moda");
    CategoryDTO updatedDTO = new CategoryDTO(id, "Vestuário", "Moda");

    when(repository.findById(id)).thenReturn(foundCategory);
    when(repository.save(foundCategory)).thenReturn(updated);
    CategoryDTO result = categoryService.updateCategory(id, updatedDTO);

    assertEquals("Vestuário", result.getName());
    assertEquals("Moda", result.getDescription());
    assertEquals(id, result.getId());

    verify(repository).save(foundCategory);
    ;
  }

  @Test
  @DisplayName("Should delete a category")
  public void shouldDeleteCategory() {
    UUID id = UUID.randomUUID();
    when(repository.delete(id)).thenReturn(true);

    categoryService.deleteCategory(id);

    verify(repository).delete(id);
  }

  @Test
  @DisplayName("Should return a category by ID")
  public void shouldReturnCategoryById() {
    UUID id = UUID.randomUUID();
    Category foundCategory = new Category(id, "Vestuário", "Roupa e Acessórios");

    when(repository.findById(id)).thenReturn(foundCategory);
    CategoryDTO result = categoryService.getCategoryById(id);

    assertEquals("Vestuário", result.getName());
    assertEquals("Roupa e Acessórios", result.getDescription());
    assertEquals(id, result.getId());

    verify(repository).findById(id);
  }

  @Test
  @DisplayName("Should return null when category not found by ID")
  public void shouldReturnNullWhenCategoryNotFoundById() {
    UUID id = UUID.randomUUID();

    when(repository.findById(id)).thenReturn(null);
    CategoryDTO result = categoryService.getCategoryById(id);

    assertEquals(null, result);

    verify(repository).findById(id);
  }

  @Test
  @DisplayName("Should return null when updating a non-existing category")
  public void shouldReturnNullWhenUpdatingNonExistingCategory() {
    UUID id = UUID.randomUUID();
    CategoryDTO updatedCategory = new CategoryDTO(id, "Vestuário", "Roupa e Acessórios");

    when(repository.findById(id)).thenReturn(null);
    CategoryDTO result = categoryService.updateCategory(id, updatedCategory);

    assertEquals(null, result);

    verify(repository).findById(id);
  }
}
