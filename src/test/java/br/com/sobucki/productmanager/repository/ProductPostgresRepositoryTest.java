package br.com.sobucki.productmanager.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.sobucki.productmanager.model.Product;

@SpringBootTest
@ActiveProfiles("test")
class ProductPostgresRepositoryTest {

    @Autowired
    private ProductPostgresRepository repository;

    @Autowired
    private ProductJpaRepository jpaRepository;

    @AfterEach
    void cleanup() {
        jpaRepository.deleteAll();
    }

    @Test
    void shouldSaveProduct() {
        // given
        Product product = new Product(null, "Produto Teste", "Descrição do Produto", new BigDecimal("10.99"));

        // when
        Product savedProduct = repository.save(product);

        // then
        assertNotNull(savedProduct.getId());
        assertEquals("Produto Teste", savedProduct.getName());
        assertEquals("Descrição do Produto", savedProduct.getDescription());
        assertEquals(new BigDecimal("10.99"), savedProduct.getPrice());
    }

    @Test
    void shouldFindProductById() {
        // given
        Product product = new Product(null, "Produto Teste", "Descrição do Produto", new BigDecimal("10.99"));
        Product savedProduct = repository.save(product);

        // when
        Product foundProduct = repository.findById(savedProduct.getId());

        // then
        assertNotNull(foundProduct);
        assertEquals(savedProduct.getId(), foundProduct.getId());
        assertEquals(savedProduct.getName(), foundProduct.getName());
    }

    @Test
    void shouldReturnNullWhenProductNotFound() {
        // when
        Product foundProduct = repository.findById(999L);

        // then
        assertNull(foundProduct);
    }

    @Test
    void shouldFindAllProducts() {
        // given
        Product product1 = new Product(null, "Produto 1", "Descrição 1", new BigDecimal("10.99"));
        Product product2 = new Product(null, "Produto 2", "Descrição 2", new BigDecimal("20.99"));
        repository.save(product1);
        repository.save(product2);

        // when
        List<Product> products = repository.findAll();

        // then
        assertEquals(2, products.size());
    }

    @Test
    void shouldDeleteProduct() {
        // given
        Product product = new Product(null, "Produto Teste", "Descrição do Produto", new BigDecimal("10.99"));
        Product savedProduct = repository.save(product);

        // when
        boolean result = repository.delete(savedProduct.getId());

        // then
        assertTrue(result);
        assertNull(repository.findById(savedProduct.getId()));
    }

    @Test
    void shouldReturnFalseWhenDeletingNonExistentProduct() {
        // when
        boolean result = repository.delete(999L);

        // then
        assertFalse(result);
    }
}