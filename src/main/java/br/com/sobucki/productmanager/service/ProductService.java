package br.com.sobucki.productmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sobucki.productmanager.model.Product;
import br.com.sobucki.productmanager.repository.ProductRepository;

@Service
public class ProductService {
  
  private final ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  public Product getProductById(Long id) {
    return productRepository.findById(id);
  }

  public Product createProduct(Product product) {
    return productRepository.save(product);
  }

  public Product updateProduct(Long id, Product product) {
    return productRepository.update(id, product);
  }

  public void deleteProduct(Long id) {
    productRepository.delete(id);
  }
}
