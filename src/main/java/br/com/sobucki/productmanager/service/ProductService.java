package br.com.sobucki.productmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.sobucki.productmanager.model.Product;

@Service
public class ProductService {
  private List<Product> products = new ArrayList<>();
  private Long nextId = 1L;

  public ProductService() {
  }

  public List<Product> getAllProducts() {
    return products;
  }

  public Product getProductById(Long id) {
    return products.stream()
        .filter(product -> id.equals(product.getId()))
        .findFirst()
        .orElse(null);
  }

  public Product createProduct(Product product) {
    product.setId(nextId++);
    products.add(product);
    return product;
  }

  public Product updateProduct(Long id, Product product) {
    Product existingProduct = getProductById(id);
    if (existingProduct != null) {
      existingProduct.setName(product.getName());
      existingProduct.setDescription(product.getDescription());
      existingProduct.setPrice(product.getPrice());
    }
    return existingProduct;
  }

  public void deleteProduct(Long id) {
    products.removeIf(product -> product.getId().equals(id));
  }
}
