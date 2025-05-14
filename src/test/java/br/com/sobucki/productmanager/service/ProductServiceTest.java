package br.com.sobucki.productmanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.argThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.sobucki.productmanager.dto.ProductDTO;
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
    UUID id = UUID.randomUUID();
    List<Product> mockProducts = List.of(new Product(id, "Tênis", "Tênis Nike", new BigDecimal("199.90")));

    when(repository.findAll()).thenReturn(mockProducts);
    List<Product> products = productService.getAllProducts();

    assertEquals(1, products.size());
    assertEquals("Tênis", products.get(0).getName());
    assertEquals("Tênis Nike", products.get(0).getDescription());
    assertEquals(new BigDecimal("199.90"), products.get(0).getPrice());
    assertEquals(id, products.get(0).getId());

    verify(repository).findAll();
  }

  @Test
  @DisplayName("Should return empty list when no products are found")
  void shouldReturnEmptyListWhenNoProductsFound() {
    when(repository.findAll()).thenReturn(List.of());
    List<Product> products = productService.getAllProducts();

    assertEquals(0, products.size());

    verify(repository).findAll();
  }

  @Test
  @DisplayName("Should return a product by ID")
  void shouldReturnProductById() {
    UUID id = UUID.randomUUID();
    Product mockProduct = new Product(id, "Tênis", "Tênis Nike", new BigDecimal("199.90"));

    when(repository.findById(id)).thenReturn(mockProduct);
    Product product = productService.getProductById(id);

    assertEquals("Tênis", product.getName());
    assertEquals("Tênis Nike", product.getDescription());
    assertEquals(new BigDecimal("199.90"), product.getPrice());
    assertEquals(id, product.getId());

    verify(repository).findById(id);
  }

  @Test
  @DisplayName("Should return null when product not found by ID")
  void shouldReturnNullWhenProductNotFoundById() {
    UUID id = UUID.randomUUID();

    when(repository.findById(id)).thenReturn(null);
    Product product = productService.getProductById(id);

    assertEquals(null, product);

    verify(repository).findById(id);
  }

  @Test
  @DisplayName("Should create a new product")
  void shouldCreateNewProduct() {
    UUID id = UUID.randomUUID();
    ProductDTO mockProduct = new ProductDTO(null, "Tênis", "Tênis Nike", new BigDecimal("199.90"));
    Product savedProduct = new Product(id, "Tênis", "Tênis Nike", new BigDecimal("199.90"));

    when(repository.save(any(Product.class))).thenReturn(savedProduct);
    Product product = productService.createProduct(mockProduct);

    assertEquals("Tênis", product.getName());
    assertEquals("Tênis Nike", product.getDescription());
    assertEquals(new BigDecimal("199.90"), product.getPrice());
    assertEquals(id, product.getId());

    verify(repository).save(any(Product.class));
    BigDecimal expectedPrice = new BigDecimal("199.90");

    verify(repository).save(argThat(p -> "Tênis".equals(p.getName()) &&
        "Tênis Nike".equals(p.getDescription()) &&
        expectedPrice.equals(p.getPrice())));
  }

  @Test
  @DisplayName("Should update an existing product")
  void shouldUpdateExistingProduct() {
    UUID id = UUID.randomUUID();

    Product existisProduct = new Product(id, "Tênis", "Tênis Nike", new BigDecimal("199.90"));
    Product updatedProduct = new Product(id, "Tênis", "Tênis Adidas", new BigDecimal("299.90"));

    when(repository.findById(id)).thenReturn(existisProduct);
    when(repository.save(existisProduct)).thenReturn(updatedProduct);
    Product product = productService.updateProduct(id, updatedProduct);

    assertEquals("Tênis", product.getName());
    assertEquals("Tênis Adidas", product.getDescription());
    assertEquals(new BigDecimal("299.90"), product.getPrice());
    assertEquals(id, product.getId());

    verify(repository).save(existisProduct);
  }

  @Test
  @DisplayName("Should return null when updating a non-existing product")
  void shouldReturnNullWhenUpdatingANonExistingProduct() {
    UUID id = UUID.randomUUID();
    when(repository.findById(id)).thenReturn(null);
    Product updatedProduct = new Product(id, "Camiseta", "Camiseta de corrida", new BigDecimal("99.90"));
    Product product = productService.updateProduct(id, updatedProduct);

    assertEquals(product, null);
    verify(repository, times(0)).save(updatedProduct);
  }

  @Test
  @DisplayName("Should delete a product by ID")
  void shouldDeleteProductById() {
    UUID id = UUID.randomUUID();
    productService.deleteProduct(id);

    verify(repository).delete(id);
  }

}
