package br.com.sobucki.productmanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.sobucki.productmanager.model.Product;
import br.com.sobucki.productmanager.repository.ProductRepository;

public class ProductServiceTest {
  private ProductRepository repository;
  private ProductService productService;

  @BeforeEach
  void setUp() {
    repository = mock(ProductRepository.class);
    productService = new ProductService(repository);
  }

  @Test
  @DisplayName("Should return all products")
  void shouldReturnAllProducts() {
    List<Product> mockProducts = List.of(new Product(1L, "Tênis", "Tênis Nike", "199.90"));

    when(repository.findAll()).thenReturn(mockProducts);
    List<Product> products = productService.getAllProducts();

    assertEquals(1, products.size());
    assertEquals("Tênis", products.get(0).getName());
    assertEquals("Tênis Nike", products.get(0).getDescription());
    assertEquals("199.90", products.get(0).getPrice());
    assertEquals(1L, products.get(0).getId());

    verify(repository).findAll();
  }

}
