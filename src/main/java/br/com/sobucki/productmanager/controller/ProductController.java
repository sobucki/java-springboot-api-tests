package br.com.sobucki.productmanager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sobucki.productmanager.model.Product;
import br.com.sobucki.productmanager.service.ProductService;

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
  public List<Product> getAllProducts() {
    return productService.getAllProducts();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getProductById(@PathVariable Long id) {
    Product product = productService.getProductById(id);
    if (product == null) {
      Map<String, String> error = new HashMap<>();
      error.put("error", "Product not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    return ResponseEntity.ok(product);
  }

  // @PostMapping("/products")
  // public Product createProduct(@RequestBody Product product) {
  // return productService.createProduct(product);
  // }

  // @PutMapping("/products/{id}")
  // public Product updateProduct(@PathVariable Long id, @RequestBody Product
  // product) {
  // return productService.updateProduct(id, product);
  // }

  // @DeleteMapping("/products/{id}")
  // public void deleteProduct(@PathVariable Long id) {
  // productService.deleteProduct(id);
  // }
}
