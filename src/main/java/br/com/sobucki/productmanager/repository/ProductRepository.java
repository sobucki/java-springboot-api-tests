package br.com.sobucki.productmanager.repository;

import java.util.List;

import br.com.sobucki.productmanager.model.Product;

public interface ProductRepository {
  List<Product> findAll();
  Product findById(Long id);
  Product save(Product product);
  Product update(Long id, Product product);
  void delete(Long id);
} 