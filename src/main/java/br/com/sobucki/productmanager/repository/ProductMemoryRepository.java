package br.com.sobucki.productmanager.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.sobucki.productmanager.model.Product;

@Repository
public class ProductMemoryRepository implements ProductRepository {
  private List<Product> products = new ArrayList<>();
  private Long nextId = 1L;

  @Override
  public List<Product> findAll() {
    return products;
  }

  @Override
  public Product findById(Long id) {
    return products.stream()
        .filter(product -> id.equals(product.getId()))
        .findFirst()
        .orElse(null);
  }

  @Override
  public Product save(Product product) {
    if (product.getId() == null) {
      product.setId(nextId++);
      products.add(product);
    } else {
      delete(product.getId());
      products.add(product);
    }
    return product;
  }

  @Override
  public void delete(Long id) {
    products.removeIf(product -> product.getId().equals(id));
  }
}