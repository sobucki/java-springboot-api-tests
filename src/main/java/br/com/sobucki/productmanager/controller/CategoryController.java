package br.com.sobucki.productmanager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sobucki.productmanager.dto.CategoryDTO;
import br.com.sobucki.productmanager.model.Category;
import br.com.sobucki.productmanager.service.CategoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping({ "", "/" })
  public List<CategoryDTO> getAllCategories() {
    return categoryService.getAllCategories();
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<?> getCategoryById(@PathVariable UUID id) {
    CategoryDTO category = categoryService.getCategoryById(id);
    if (category == null) {
      Map<String, String> error = new HashMap<>();
      error.put("error", "Category not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    return ResponseEntity.ok(category);
  }
  
  @PostMapping({ "", "/" })
  public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
    CategoryDTO created = categoryService.createCategory(categoryDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<?> updateCategory(@PathVariable UUID id, @RequestBody @Valid CategoryDTO categoryDTO) {
    Category category = categoryService.createCategoryFromDTO(categoryDTO);
    CategoryDTO updated = categoryService.updateCategory(id, category);
    if (updated == null) {
      Map<String, String> error = new HashMap<>();
      error.put("error", "Category not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    return ResponseEntity.ok(updated);
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCategory(@PathVariable UUID id) {
    CategoryDTO existingCategory = categoryService.getCategoryById(id);
    if (existingCategory == null) {
      Map<String, String> error = new HashMap<>();
      error.put("error", "Category not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    categoryService.deleteCategory(id);
    return ResponseEntity.noContent().build();
  }
}
