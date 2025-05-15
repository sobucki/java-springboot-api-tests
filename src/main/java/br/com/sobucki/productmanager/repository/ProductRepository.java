package br.com.sobucki.productmanager.repository;

import java.util.List;
import java.util.UUID;

import br.com.sobucki.productmanager.model.Product;

public interface ProductRepository {
  List<Product> findAll();

  Product findById(UUID id);

  Product save(Product product);

  boolean delete(UUID id);
  
  List<Product> saveAll(List<Product> products);
  
  void deleteAll();
}