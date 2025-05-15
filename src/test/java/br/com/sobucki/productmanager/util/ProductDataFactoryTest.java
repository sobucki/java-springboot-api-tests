package br.com.sobucki.productmanager.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.sobucki.productmanager.model.Product;

public class ProductDataFactoryTest {

    @Test
    @DisplayName("Deve gerar produto com nome realista usando JavaFaker")
    public void shouldGenerateRealisticProductWithJavaFaker() {
        // When
        Product product = ProductDataFactory.createRealisticProduct();
        
        // Then
        assertNotNull(product);
        assertNotNull(product.getName());
        assertNotNull(product.getDescription());
        assertNotNull(product.getPrice());
        
        // Imprime os detalhes do produto para verificação visual
        System.out.println("===== Produto Gerado =====");
        System.out.println("Nome: " + product.getName());
        System.out.println("Descrição: " + product.getDescription());
        System.out.println("Preço: R$ " + product.getPrice());
    }
    
    @Test
    @DisplayName("Deve gerar múltiplos produtos com nomes diferentes")
    public void shouldGenerateMultipleProductsWithDifferentNames() {
        // Given
        int quantity = 5;
        List<Product> products = new ArrayList<>();
        
        // When
        for (int i = 0; i < quantity; i++) {
            products.add(ProductDataFactory.createRealisticProduct());
        }
        
        // Then
        for (int i = 0; i < products.size(); i++) {
            Product current = products.get(i);
            assertNotNull(current.getName());
            
            // Imprime os nomes dos produtos para verificação
            System.out.println("Produto " + (i+1) + ": " + current.getName() + " - R$ " + current.getPrice());
            
            // Verifica se não há nomes duplicados
            for (int j = i + 1; j < products.size(); j++) {
                Product other = products.get(j);
                assertTrue(!current.getName().equals(other.getName()), 
                        "Produtos não devem ter nomes idênticos");
            }
        }
    }
} 