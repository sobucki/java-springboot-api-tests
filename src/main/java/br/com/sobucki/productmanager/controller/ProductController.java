package br.com.sobucki.productmanager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sobucki.productmanager.dto.ProductDTO;
import br.com.sobucki.productmanager.model.Product;
import br.com.sobucki.productmanager.service.ProductService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping({ "", "/" })
  public List<ProductDTO> getAllProducts() {
    return productService.getAllProducts();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getProductById(@PathVariable UUID id) {
    ProductDTO product = productService.getProductById(id);
    if (product == null) {
      Map<String, String> error = new HashMap<>();
      error.put("error", "Product not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    return ResponseEntity.ok(product);
  }

  @PostMapping({ "", "/" })
  public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDTO productDTO) {
    ProductDTO created = productService.createProduct(productDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateProduct(@PathVariable UUID id, @RequestBody ProductDTO product) {
    ProductDTO existingProduct = productService.getProductById(id);
    if (existingProduct == null) {
      Map<String, String> error = new HashMap<>();
      error.put("error", "Product not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    return ResponseEntity.ok(productService.updateProduct(id, product));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
    ProductDTO existingProduct = productService.getProductById(id);
    if (existingProduct == null) {
      Map<String, String> error = new HashMap<>();
      error.put("error", "Product not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }
}
