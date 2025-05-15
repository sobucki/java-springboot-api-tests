package br.com.sobucki.productmanager.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import br.com.sobucki.productmanager.model.Product;

@Repository
public class ProductMemoryRepository implements ProductRepository {
  private List<Product> products = new ArrayList<>();

  @Override
  public List<Product> findAll() {
    return products;
  }

  @Override
  public Product findById(UUID id) {
    return products.stream()
        .filter(product -> id.equals(product.getId()))
        .findFirst()
        .orElse(null);
  }

  @Override
  public Product save(Product product) {
    if (product.getId() == null) {
      product.setId(UUID.randomUUID());
      products.add(product);
    } else {
      delete(product.getId());
      products.add(product);
    }
    return product;
  }

  @Override
  public boolean delete(UUID id) {
    return products.removeIf(product -> product.getId().equals(id));
  }
  
  @Override
  public List<Product> saveAll(List<Product> products) {
    List<Product> savedProducts = new ArrayList<>();
    for (Product product : products) {
      savedProducts.add(save(product));
    }
    return savedProducts;
  }
  
  @Override
  public void deleteAll() {
    products.clear();
  }
}