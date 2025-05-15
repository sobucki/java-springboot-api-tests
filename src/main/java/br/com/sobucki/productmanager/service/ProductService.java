package br.com.sobucki.productmanager.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sobucki.productmanager.dto.ProductDTO;
import br.com.sobucki.productmanager.model.Product;
import br.com.sobucki.productmanager.repository.product.ProductRepository;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<ProductDTO> getAllProducts() {
    List<Product> allProducts = productRepository.findAll();
    List<ProductDTO> allProductsDTO = allProducts.stream().map(product -> createProductDTO(product)).toList();
    return allProductsDTO;
  }

  public Product getProductById(UUID id) {
    return productRepository.findById(id);
  }

  public Product createProduct(ProductDTO dto) {
    Product product = createProductFromDTO(dto);
    return productRepository.save(product);
  }

  public Product updateProduct(UUID id, Product product) {
    Product existingProduct = productRepository.findById(id);
    if (existingProduct == null) {
      return null;
    }

    existingProduct.setName(product.getName());
    existingProduct.setDescription(product.getDescription());
    existingProduct.setPrice(product.getPrice());

    return productRepository.save(existingProduct);
  }

  public void deleteProduct(UUID id) {
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

  public ProductDTO createProductDTO(Product product) {
    ProductDTO productDTO = new ProductDTO();
    productDTO.setName(product.getName());
    productDTO.setDescription(product.getDescription());
    productDTO.setPrice(product.getPrice());
    productDTO.setId(product.getId());
    return productDTO;
  }
}
