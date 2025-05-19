package br.com.sobucki.productmanager.repository.product;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sobucki.productmanager.model.Product;

// @Repository
public interface ProductJpaRepository extends JpaRepository<Product, UUID> {
    // Métodos personalizados podem ser adicionados aqui
}