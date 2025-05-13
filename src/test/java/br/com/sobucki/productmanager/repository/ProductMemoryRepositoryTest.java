package br.com.sobucki.productmanager.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.sobucki.productmanager.model.Product;

public class ProductMemoryRepositoryTest {
  private ProductMemoryRepository repository;

  @BeforeEach
  void setUp() {
    repository = new ProductMemoryRepository();
  }

  @Test
  @DisplayName("Should save a product")
  void shouldSaveProduct() {
    Product product = new Product(null, "Tênis", "Tênis Nike", new BigDecimal("199.90"));
    Product savedProduct = repository.save(product);

    assertNotNull(savedProduct.getId());
    assertEquals("Tênis", savedProduct.getName());
    assertEquals("Tênis Nike", savedProduct.getDescription());
    assertEquals(new BigDecimal("199.90"), savedProduct.getPrice());
  }

  @Test
  @DisplayName("Should return all products")
  void shouldReturnAllProducts() {
    Product product1 = new Product(1L, "Tênis", "Tênis Nike", new BigDecimal("199.90"));
    Product product2 = new Product(2L, "Camisa", "Camisa Adidas", new BigDecimal("99.90"));
    repository.save(product1);
    repository.save(product2);

    assertEquals(2, repository.findAll().size());
  }

  @Test
  @DisplayName("Should return a product by ID")
  void shouldReturnProductById() {
    Product product = new Product(1L, "Tênis", "Tênis Nike", new BigDecimal("199.90"));
    repository.save(product);

    Product foundProduct = repository.findById(1L);

    assertNotNull(foundProduct);
    assertEquals("Tênis", foundProduct.getName());
    assertEquals("Tênis Nike", foundProduct.getDescription());
    assertEquals(new BigDecimal("199.90"), foundProduct.getPrice());
  }

  @Test
  @DisplayName("Should return null when product not found by ID")
  void shouldReturnNullWhenProductNotFoundById() {
    Product foundProduct = repository.findById(1L);

    assertEquals(null, foundProduct);
  }

  @Test
  @DisplayName("Should delete a product by ID")
  void shouldDeleteProductById() {
    Product product = new Product(1L, "Tênis", "Tênis Nike", new BigDecimal("199.90"));
    repository.save(product);

    boolean result = repository.delete(1L);

    assertEquals(result, true);
    assertEquals(0, repository.findAll().size());
  }

  @Test
  @DisplayName("Should return false when deleting a non-existing product")
  void shouldReturnFalseWhenDeletingNonExistingProduct() {
    Product product = new Product(1L, "Tênis", "Tênis Nike", new BigDecimal("199.90"));
    repository.save(product);

    boolean result = repository.delete(2L);

    assertEquals(result, false);
    assertEquals(1, repository.findAll().size());
  }

}
