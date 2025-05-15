package br.com.sobucki.productmanager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sobucki.productmanager.dto.CategoryDTO;
import br.com.sobucki.productmanager.service.CategoryService;

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
}
