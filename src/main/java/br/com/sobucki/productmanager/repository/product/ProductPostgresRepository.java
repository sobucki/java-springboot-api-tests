package br.com.sobucki.productmanager.repository.product;

import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sobucki.productmanager.model.Product;
import br.com.sobucki.productmanager.repository.base.BaseRepositoryImpl;

@Repository
@Primary
public class ProductPostgresRepository extends BaseRepositoryImpl<Product, UUID> implements ProductRepository {

    private final ProductJpaRepository jpaRepository;

    public ProductPostgresRepository(ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    protected JpaRepository<Product, UUID> getRepository() {
        return jpaRepository;
    }

}