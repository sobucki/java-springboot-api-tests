package br.com.sobucki.productmanager.service;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

import java.util.List;

import br.com.sobucki.productmanager.dto.CategoryDTO;
import br.com.sobucki.productmanager.repository.category.CategoryRepository;

public class CategoryServiceTest {

  private CategoryRepository repository;
  private CategoryService productService;

  @BeforeEach
  void setUp() {
    repository = mock(CategoryRepository.class);
    productService = new CategoryService(repository);
  }

  @Test
  @DisplayName("Should return all categories")
  void shouldReturnAllCategories() {
    when(repository.findAll()).thenReturn(List.of());
    List<CategoryDTO> categories = productService.getAllCategories();

    assertEquals(0, categories.size());
    verify(repository).findAll();
  }
}
