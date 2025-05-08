package br.com.sobucki.productmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.sobucki.productmanager.model.Product;

@Service
public class ProductService {
  private List<Product> products = new ArrayList<>();

  public ProductService() {
    // products.add(new Product(1L, "Tênis", "Tênis Nike", "199.90"));
  }

  public List<Product> getAllProducts() {
    return products;
  }

  public Product getProductById(Long id) {
    return products.stream().filter(
        product -> product.getId().equals(id)).findFirst().orElse(null);
  }
}
