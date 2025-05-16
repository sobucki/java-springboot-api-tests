package br.com.sobucki.productmanager.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sobucki.productmanager.dto.ProductDTO;
import br.com.sobucki.productmanager.model.Category;
import br.com.sobucki.productmanager.model.Product;
import br.com.sobucki.productmanager.repository.category.CategoryRepository;
import br.com.sobucki.productmanager.repository.product.ProductRepository;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  @Autowired
  public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
  }

  public List<ProductDTO> getAllProducts() {
    List<Product> allProducts = productRepository.findAll();
    List<ProductDTO> allProductsDTO = allProducts.stream().map(product -> createProductDTO(product)).toList();
    return allProductsDTO;
  }

  public Product getProductById(UUID id) {
    return productRepository.findById(id);
  }

  @Transactional
  public Product createProduct(ProductDTO dto) {
    Product product = createProductFromDTO(dto);
    return productRepository.save(product);
  }

  @Transactional
  public Product updateProduct(UUID id, ProductDTO productDTO) {
    Product existingProduct = productRepository.findById(id);
    if (existingProduct == null) {
      return null;
    }

    existingProduct.setName(productDTO.getName());
    existingProduct.setDescription(productDTO.getDescription());
    existingProduct.setPrice(productDTO.getPrice());
    
    // Atualizar categorias
    updateProductCategories(existingProduct, productDTO.getCategoryIds());

    return productRepository.save(existingProduct);
  }

  @Transactional
  public void deleteProduct(UUID id) {
    productRepository.delete(id);
  }

  @Transactional
  public Product createProductFromDTO(ProductDTO productDTO) {
    Product product = new Product();
    product.setName(productDTO.getName());
    product.setDescription(productDTO.getDescription());
    product.setPrice(productDTO.getPrice());
    product.setId(productDTO.getId());
    
    // Associar categorias
    if (productDTO.getCategoryIds() != null && !productDTO.getCategoryIds().isEmpty()) {
      updateProductCategories(product, productDTO.getCategoryIds());
    }
    
    return product;
  }

  public ProductDTO createProductDTO(Product product) {
    ProductDTO productDTO = new ProductDTO();
    productDTO.setName(product.getName());
    productDTO.setDescription(product.getDescription());
    productDTO.setPrice(product.getPrice());
    productDTO.setId(product.getId());
    
    // Incluir IDs das categorias
    if (product.getCategories() != null) {
      Set<UUID> categoryIds = product.getCategories().stream()
          .map(Category::getId)
          .collect(Collectors.toSet());
      productDTO.setCategoryIds(categoryIds);
    }
    
    return productDTO;
  }
  
  private void updateProductCategories(Product product, Set<UUID> categoryIds) {
    if (categoryIds == null) {
      return;
    }
    
    // Limpar categorias existentes
    product.getCategories().clear();
    
    // Adicionar novas categorias
    for (UUID categoryId : categoryIds) {
      Category category = categoryRepository.findById(categoryId);
      if (category != null) {
        product.addCategory(category);
      }
    }
  }
}
