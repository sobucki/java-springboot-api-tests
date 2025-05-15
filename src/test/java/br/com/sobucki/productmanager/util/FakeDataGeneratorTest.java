package br.com.sobucki.productmanager.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.sobucki.productmanager.model.Product;

public class FakeDataGeneratorTest {

    @Test
    @DisplayName("Deve gerar um produto com valores aleatórios")
    public void shouldGenerateRandomProduct() {
        // When
        Product product = FakeDataGenerator.generate(Product.class);
        
        // Then
        assertNotNull(product);
        assertNotNull(product.getName());
        assertNotNull(product.getDescription());
        assertNotNull(product.getPrice());
    }
    
    @Test
    @DisplayName("Deve gerar uma lista de produtos com a quantidade especificada")
    public void shouldGenerateProductsList() {
        // Given
        int quantity = 5;
        
        // When
        List<Product> products = FakeDataGenerator.generate(Product.class, quantity);
        
        // Then
        assertEquals(quantity, products.size());
        products.forEach(product -> {
            assertNotNull(product.getName());
            assertNotNull(product.getDescription());
            assertNotNull(product.getPrice());
        });
    }
    
    @Test
    @DisplayName("Deve gerar produtos usando um fornecedor personalizado")
    public void shouldGenerateProductsWithCustomSupplier() {
        // Given
        int quantity = 3;
        Supplier<Product> supplier = () -> {
            Product product = new Product();
            product.setName("Produto Test");
            product.setDescription("Descrição padrão");
            product.setPrice(new BigDecimal("99.99"));
            return product;
        };
        
        // When
        List<Product> products = FakeDataGenerator.generate(supplier, quantity);
        
        // Then
        assertEquals(quantity, products.size());
        products.forEach(product -> {
            assertEquals("Produto Test", product.getName());
            assertEquals("Descrição padrão", product.getDescription());
            assertEquals(new BigDecimal("99.99"), product.getPrice());
        });
    }
    
    @Test
    @DisplayName("Deve gerar produtos com preço no intervalo especificado")
    public void shouldGenerateProductWithPriceRange() {
        // Given
        BigDecimal minPrice = new BigDecimal("50.00");
        BigDecimal maxPrice = new BigDecimal("100.00");
        
        // When
        Product product = FakeDataGenerator.generateProductWithPriceRange(minPrice, maxPrice);
        
        // Then
        assertNotNull(product);
        assertTrue(product.getPrice().compareTo(minPrice) >= 0);
        assertTrue(product.getPrice().compareTo(maxPrice) <= 0);
    }
} 