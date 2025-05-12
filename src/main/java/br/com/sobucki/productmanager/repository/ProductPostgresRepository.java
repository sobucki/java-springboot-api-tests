package br.com.sobucki.productmanager.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import br.com.sobucki.productmanager.model.Product;

@Repository
@Primary
public class ProductPostgresRepository implements ProductRepository {

    private final ProductJpaRepository jpaRepository;

    public ProductPostgresRepository(ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return jpaRepository.findById(id).orElse(null);
    }

    @Override
    public Product save(Product product) {
        return jpaRepository.save(product);
    }

    @Override
    public boolean delete(Long id) {
        if (jpaRepository.existsById(id)) {
            jpaRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 