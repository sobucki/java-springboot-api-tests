package br.com.sobucki.productmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sobucki.productmanager.dto.ProductDTO;
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

  public Product createProduct(ProductDTO dto) {
    Product product = createProductFromDTO(dto);
    return productRepository.save(product);
  }

  public Product updateProduct(Long id, Product product) {
    Product existingProduct = productRepository.findById(id);
    if (existingProduct == null) {
      return null;
    }

    existingProduct.setName(product.getName());
    existingProduct.setDescription(product.getDescription());
    existingProduct.setPrice(product.getPrice());

    return productRepository.save(existingProduct);
  }

  public void deleteProduct(Long id) {
    productRepository.delete(id);
  }

  public Product createProductFromDTO(ProductDTO productDTO) {
    Product product = new Product();
    product.setName(productDTO.getName());
    product.setDescription(productDTO.getDescription());
    product.setPrice(productDTO.getPrice());
    product.setId(productDTO.getId());
    return product;
  }
}
